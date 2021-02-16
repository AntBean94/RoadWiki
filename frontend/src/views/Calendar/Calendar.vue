<template>
    <div>
        <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default"></base-header> 
        <b-container fluid class="mt--7">
		
        
        <!-- 캘린더 CURD -->
      	<b-row>
			<b-col>
				<b-card no-body class="border-0">

					<div id="calendar">
						<div class="calendar-controls">
							<div class>
								<!-- 모달관련 -->
								<div>
									<b-button v-b-modal.modal-scrollable>상세일정보기</b-button>

									<b-modal id="modal-scrollable" scrollable title="로드위키">
										<!-- 일정상세정보 -->
										<div class="field">
											<label class="label">Title</label>
											<div class="control my-2">
												{{ this.showTitle}}
											</div>
										</div>
										<div class="field">
											<label class="label">Start date</label>
											<div class="control">
												<input v-model="newItemStartDate" class="input" type="date" />
											</div>
										</div>
										<div class="field">
											<label class="label">End date</label>
											<div class="control">
												<input v-model="newItemEndDate" class="input" type="date" />
											</div>
										</div>
										<base-button id="updateScheduleBtn" class="my-3" @click="clickUpdateItem" :disabled="disabledBtn">
										Udpate schedule
										</base-button>
										<base-button id="deleteScheduleBtn" class="my-3 " @click="clickDeleteItem" :disabled="disabledBtn">
										Delete schedule
										</base-button>
									</b-modal>
								</div>
							</div>
						</div>
						<div class="calendar-parent">
							<calendar-view
								:items="items"
								:show-date="showDate"
								:time-format-options="{ hour: 'numeric', minute: '2-digit' }"
								:enable-drag-drop="true"
								:disable-past="disablePast"
								:disable-future="disableFuture"
								:class="themeClasses"
								:period-changed-callback="periodChanged"
								:current-period-label="useTodayIcons ? 'icons' : ''"
								:displayWeekNumbers="displayWeekNumbers"
								:enable-date-selection="true"
								:selection-start="selectionStart"
								:selection-end="selectionEnd"
								@date-selection-start="setSelection"
								@date-selection="setSelection"
								@date-selection-finish="finishSelection"
								@drop-on-date="onDrop"
								@click-date="onClickDay"
								@click-item="onClickItem"
							>
								<calendar-view-header
									slot="header"
									slot-scope="{ headerProps }"
									:header-props="headerProps"
									@input="setShowDate"
								/>
							</calendar-view>
						</div>
					</div>

				</b-card>
			</b-col>
      </b-row>
    </b-container> 
    </div>
    
</template>

<script>
require("vue-simple-calendar/static/css/default.css")
require("vue-simple-calendar/static/css/holidays-us.css")

import {
	CalendarView,
	CalendarViewHeader,
	CalendarMathMixin,
} from "vue-simple-calendar"

export default {
	name: "App",
	components: {
		CalendarView,
		CalendarViewHeader,
	},
	mixins: [CalendarMathMixin],
	data() {
		return {
			username: '',
			// 버튼 보여주기
			disabledBtn: true,
			// calendar 
			itemId : "", 
			showTitle: "",
			showDate: this.today(),
			message: "",
			startingDayOfWeek: 0,
			disablePast: false,
			disableFuture: false,
			displayPeriodUom: "month",
			displayPeriodCount: 1,
			displayWeekNumbers: false,
			selectionStart: null,
			selectionEnd: null,
			// update관련 data
			newItemSdid: "",
			newItemMdid: "",
			newItemBdid: "",
			newItemKey: "",
			newItemRmid: "",
			newItemTitle: "",
			newItemStartDate: "",
			newItemEndDate: "",
			newItemMemo: "",
			useDefaultTheme: true,
			useHolidayTheme: true,
			useTodayIcons: false,
			items: [],
		}
	},
 	created(){
		// 사용자 커리큘럼 및 일정 로드해 오기 
		const uid = String(this.$store.getters.getUid)

		// 해당 유저에 대한 정보 백앤드에 전달
		axios.get(`${this.$store.getters.getServer}/calendar/get/${uid}`)
				.then((res) => {
						res.data['calendars'].map(curr => {
								let item = {}
								item.key = curr.key;
								item.rmid = curr.rmid;
								item.startDate = curr.startdate;
								item.endDate = curr.enddate;
								item.title = curr.text;
								item.sdid = curr.sdid;
								item.mdid = curr.mdid;
								item.bdid = curr.bdid;
								item.memo = curr.memo;
								
								if (curr.category==='blue') {
										item.classes = 'blue'
								} else if (curr.category==='black') {
										item.classes = 'orange'
								} else if (curr.category === 'green'){
										item.classes = 'purple'
								} else{
									item.classes = 'yellow'
								}
								this.items.push(item)
						})
			}).catch((e)=> {
					console.log(e)
					alert('axios 통신오류')
			})
    },
	computed: {
		userLocale() {
			return this.getDefaultBrowserLocale
		},
		dayNames() {
			return this.getFormattedWeekdayNames(this.userLocale, "long", 0)
		},
		// 아이콘 및 테마 설정 
		themeClasses() {
			return {
				"theme-default": this.useDefaultTheme,
				"holiday-us-traditional": this.useHolidayTheme,
				"holiday-us-official": this.useHolidayTheme,
			}
		},
		
	},
	mounted() {
		this.username = this.$store.getters.getName

		this.newItemStartDate = this.isoYearMonthDay(this.today())
		this.newItemEndDate = this.isoYearMonthDay(this.today())
	},
	methods: {
		periodChanged() {
			// range, eventSource) {
			// Demo does nothing with this information, just including the method to demonstrate how
			// you can listen for changes to the displayed range and react to them (by loading items, etc.)
			//console.log(eventSource)
			//console.log(range)
		},
		onClickDay(d) {
			this.disabledBtn = true
			this.selectionStart = null
			this.selectionEnd = null
			this.newItemStartDate = this.isoYearMonthDay(d)
			this.newItemEndDate = this.isoYearMonthDay(d)
			this.newItemMemo = ""
			this.message = `날짜 : ${d.toLocaleDateString()}`
		},
		// 해당 item선택 
		onClickItem(e) {
			this.disabledBtn = false
			this.newItemKey = e.originalItem.key;
			this.newItemRmid = e.originalItem.rmid;
			this.newItemSdid = e.originalItem.sdid;
			this.newItemBdid = e.originalItem.bdid; 
			this.newItemMdid = e.originalItem.mdid;
			this.newItemTitle = e.title
			this.newItemStartDate = this.isoYearMonthDay(e.startDate)
			this.newItemEndDate = this.isoYearMonthDay(e.endDate)
			this.showTitle = e.title
			this.message = `일정: ${e.title}`
			this.newItemMemo = e.originalItem.memo;
		},
		setShowDate(d) {
			this.showDate = d
		},
		setSelection(dateRange) {
			this.selectionEnd = dateRange[1]
			this.selectionStart = dateRange[0]
		},
		finishSelection(dateRange) {
			this.setSelection(dateRange)
			this.newItemStartDate = this.isoYearMonthDay(this.selectionStart)
			this.newItemEndDate = this.isoYearMonthDay(this.selectionEnd)
		},
		onDrop(item, date) {
			// Determine the delta between the old start date and the date chosen,
			// and apply that delta to both the start and end date to move the item.
			const eLength = this.dayDiff(item.startDate, date)
			item.originalItem.startDate = this.addDays(item.startDate, eLength)
			item.originalItem.endDate = this.addDays(item.endDate, eLength)
		},
		// 스케쥴 생성버튼 
		clickAddItem() {
			this.items.push({
				startDate: this.newItemStartDate,
				endDate: this.newItemEndDate,
				title: this.newItemTitle,
				id: "e" + Math.random().toString(36).substr(2, 10),
			})
		},
		
		//  스케쥴 수정버튼
		clickUpdateItem() {
			let updateUserData = {}
			updateUserData.rmid = this.newItemRmid
			updateUserData.key = this.newItemKey
			updateUserData.startdate = this.newItemStartDate
			updateUserData.enddate = this.newItemEndDate
			updateUserData.sdid = this.newItemSdid
			updateUserData.mdid = this.newItemMdid
			updateUserData.bdid = this.newItemBdid
			// 백엔드 서버 통신 (변경사항 전달)
			axios.put(`${this.$store.getters.getServer}/calendar/modify`, 
				updateUserData
			)
			  .then((res) => {
					this.items.map(item => {
						if (item.rmid == this.newItemRmid && item.key == this.newItemKey) {
							item.startDate = this.newItemStartDate
							item.endDate = this.newItemEndDate
							return
						}
					})
			  }).catch((e)=>{
				  console.log(e);
			  })
			alert('일정을 수정했습니다.')
		},
		// 계획일정을 지우는 함수 
		clickDeleteItem() {
			// 해당 일정 삭제''
			this.newItemStartDate = ""
			this.newItemEndDate = ""
			this.clickUpdateItem();
		},
	},
}
</script>

<style>

#calendar {
	display: flex;
	flex-direction: row;
	/* font-family: Calibri, sans-serif; */
	width: 90%;
	min-width: 30rem;
	max-width: 100rem;
	min-height: 40rem;
	margin-left: auto;
	margin-right: auto;
}
.calendar-controls {
	/* margin-left : 0.1rem; */
	margin-right : 3%;
	min-width: 12rem;
	max-width: 12rem;
}
.calendar-parent {
	display: flex;
	flex-direction: column;
	flex-grow: 1;
	overflow-x: hidden;
	overflow-y: hidden;
	max-height: 80vh;
	background-color: white;
}
/* For long calendars, ensure each week gets sufficient height. The body of the calendar will scroll if needed */
.cv-wrapper.period-month.periodCount-2 .cv-week,
.cv-wrapper.period-month.periodCount-3 .cv-week,
.cv-wrapper.period-year .cv-week {
	min-height: 6rem;
}

/* The following classes style the classes computed in myDateClasses and passed to the component's dateClasses prop. */
.theme-default .cv-day.ides {
	background-color:#dbdbdb;
}
.ides .cv-day-number::before {
	content: "\271D";
}
.theme-default .cv-day.do-you-remember.the-21st .cv-day-number::after {
	content: "\1F30D\1F32C\1F525";
}
.notification.is-success {
    background-color: lemonchiffon;
    border-radius: 6px;
    box-shadow: 0 2px 3px rgb(10 10 10 / 10%), 0 0 0 1px rgb(10 10 10 / 10%);
    color: #443600;
    display: block;
	padding: 1.25rem;
	margin-bottom: 20%;
	min-height: 10rem;
	
}
.box {
    background-color: #fff;
    border-radius: 6px;
    box-shadow: 0 2px 3px rgb(10 10 10 / 10%), 0 0 0 1px rgb(10 10 10 / 10%);
    color: #4a4a4a;
    display: block;
    padding: 1.25rem;
}
.control {
    clear: both;
    font-size: 1rem;
    position: relative;
    text-align: left;
}
.input, .textarea {
    background-color: #fff;
    border-color: #dbdbdb;
    color: #363636;
    box-shadow: inset 0 1px 2px rgb(10 10 10 / 10%);
    max-width: 100%;
    width: 100%;
}
.disabled{
	background-color: #ccc9c9c2;
}
.theme-default .cv-item.yellow {
	background-color: lemonchiffon;
	border-color: lemonchiffon;
}
.theme-default .cv-item.blue {
	background-color: paleturquoise;
	border-color: paleturquoise
}
/* 지난 날 */
.theme-default .cv-day.past {
	background-color: rgb(253, 251, 251) ;
}
.theme-default .cv-header-day{
	background-color: white;
}
/* 다가올 날 */
.theme-default .cv-day.outsideOfMonth{
	background-color: rgb(253, 251, 251) ;
}

.theme-default .cv-header, .theme-default .cv-header-day{
	background-color: white;
}
/* 헤더 속성 */
.cv-header button {
	box-sizing: border-box;
    line-height: 1em;
    font-size: 1em;
    border-width: 1px;
    background-color: white;
}
/* 오늘 날짜에표시 */
.theme-default .cv-day.today {
    background-color: white;
}
</style>