<template>
    <div>
        <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default"></base-header> 
        <b-container fluid class="mt--7">
		
        
        <!-- 캘린더 CURD -->
      	<b-row>
			<b-col>
				<b-card no-body class="border-0">
					<h1> {{ username }} 님의 일정 </h1>

					<div id="calendar">
						<div class="calendar-controls">
							<div v-if="message" class="notification is-success">{{ message }}</div>
							<div class="box">
								<div class="field">
									<label class="label">Title</label>
									<div class="control">
										<input v-model="showTitle" class="input disabled" type="text" :disabled="disabledBtn"/>
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
								:show-times="showTimes"
								:date-classes="myDateClasses"
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
			
			message: "",
			startingDayOfWeek: 0,
			disablePast: false,
			disableFuture: false,
			displayPeriodUom: "month",
			displayPeriodCount: 1,
			displayWeekNumbers: false,
			showTimes: true,
			selectionStart: null,
			selectionEnd: null,
			newItemTitle: "",
			newItemStartDate: "",
			newItemEndDate: "",
			useDefaultTheme: true,
			useHolidayTheme: true,
			useTodayIcons: false,
			items: [
				{
					id: "1",
					title: '적게 일하고 많이 벌자',
					startDate: "2021-02-05",
				},
				{
					id: "2",
					startDate: "2021-02-11",
					endDate: "2021-02-15",
					title: "집중 프론트기간",
				},
			],
		}
	},
	created(){
		// 사용자 커리큘럼 및 일정 로드해 오기 
		const uid = String(this.$store.getters.getUid)

		// 해당 유저에 대한 정보 백앤드에 전달
		axios.post(`${this.$store.getters.getServer}/calendar/${uid}`)
		  .then((res) => {
			//   this.items = res.data[]
			  console.log('154줄 통신성공')
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
			this.message = `날짜 : ${d.toLocaleDateString()}`
		},
		// 해당 item선택 
		onClickItem(e) {
			console.log(e)
			this.disabledBtn = false
			
			this.itemId = e.id 
			this.newItemTitle = e.title
			this.newItemStartDate = this.isoYearMonthDay(e.startDate)
			this.newItemEndDate = this.isoYearMonthDay(e.endDate)

			this.showTitle = e.title
			this.message = `일정: ${e.title}`
		},
		setShowDate(d) {
			this.message = `Changing calendar view to ${d.toLocaleDateString()}`
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
			this.message = `You selected: ${this.selectionStart.toLocaleDateString()} -${this.selectionEnd.toLocaleDateString()}`
		},
		onDrop(item, date) {
			this.message = `You dropped ${item.id} on ${date.toLocaleDateString()}`
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
			this.message = "You added a calendar item!"
		},
		
		//  스케쥴 수정버튼
		clickUpdateItem() {
			console.log(`수정된 시작날짜 : ${this.newItemStartDate} `)
			console.log(`수정된 종료날짜 : ${this.newItemEndDate} `)
			console.log(`해당일정의 id : ${this.itemId} `)
			// 백엔드 서버 통신 (변경사항 전달)
			axios.patch(`${this.$store.getters.getServer}/calendar/get/${itemId}`)
			  .then((res) => {
				  console.log(res)
				  console.log("256줄 axios 수정 성공")
			  }).catch((e)=>{
				  console.log("axios 오류")
			  })
			alert('일정을 수정했습니다.')
		},

		// 계획일정을 지우는 함수 
		clickDeleteItem() {
			console.log(`해당일정의 id : ${this.itemId} `)
			// 해당 일정 삭제
			axios.delete(`${this.$store.getters.getServer}/calendar/${itemId}`)
			  .then((res) => {
				  console.log("269 삭제 성공")
			  }).catch((e) => {
				  console.log("axios 통신 실패")
			  })
			alert('해당일정을 삭제했습니다.')
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
/* These styles are optional, to illustrate the flexbility of styling the calendar purely with CSS. */
/* Add some styling for items tagged with the "birthday" class */
.theme-default .cv-item.birthday {
	background-color: #e0f0e0;
	border-color: #d7e7d7;
}
.theme-default .cv-item.birthday::before {
	content: "\1F382"; /* Birthday cake */
	margin-right: 0.5em;
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
    background-color: rgb(51, 132, 238);
    border-radius: 6px;
    box-shadow: 0 2px 3px rgb(10 10 10 / 10%), 0 0 0 1px rgb(10 10 10 / 10%);
    color: #ffffff;
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
    
</style>