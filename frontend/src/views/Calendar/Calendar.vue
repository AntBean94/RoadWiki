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
										<input v-model="showTitle" class="input disabled" type="text" disabled/>
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

								<base-button class="my-3" @click="clickTestAddItem">
									Add schedule
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
			// calendar 
			showTitle: '',
			showDate: this.thisMonth(1),
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
					id: "e0",
					startDate: "2018-01-05",
				},
				{
					id: "e1",
					startDate: this.thisMonth(15, 18, 30),
					title: '너는 누구냐'
				},
				{
					id: "e2",
					startDate: this.thisMonth(15),
					title: "Single-day item with a long title",
				},
				{
					id: "e3",
					startDate: this.thisMonth(7, 9, 25),
					endDate: this.thisMonth(10, 16, 30),
					title: "Multi-day item with a long title and times",
				},
				{
					id: "e4",
					startDate: this.thisMonth(20),
					title: "My Birthday!",
					classes: "birthday",
					url: "https://en.wikipedia.org/wiki/Birthday",
				},
				{
					id: "e5",
					startDate: this.thisMonth(5),
					endDate: this.thisMonth(12),
					title: "Multi-day item",
					classes: "purple",
				},
				{
					id: "foo",
					startDate: this.thisMonth(29),
					title: "Same day 1",
				},
				{
					id: "e6",
					startDate: this.thisMonth(29),
					title: "Same day 2",
					classes: "orange",
				},
				{
					id: "e7",
					startDate: this.thisMonth(29),
					title: "Same day 3",
				},
				{
					id: "e8",
					startDate: this.thisMonth(29),
					title: "Same day 4",
					classes: "orange",
				},
				{
					id: "e9",
					startDate: this.thisMonth(29),
					title: "Same day 5",
				},
				{
					id: "e10",
					startDate: this.thisMonth(29),
					title: "Same day 6",
					classes: "orange",
				},
				{
					id: "e11",
					startDate: this.thisMonth(29),
					title: "Same day 7",
				},
			],
		}
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
		myDateClasses() {
			const o = {}
			const theFirst = this.thisMonth(1)
			const ides = [2, 4, 6, 9].includes(theFirst.getMonth()) ? 15 : 13
			const idesDate = this.thisMonth(ides)
			o[this.isoYearMonthDay(idesDate)] = "ides"
			o[this.isoYearMonthDay(this.thisMonth(21))] = [
				"do-you-remember",
				"the-21st",
			]
			return o
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
		thisMonth(d, h, m) {
			const t = new Date()
			return new Date(t.getFullYear(), t.getMonth(), d, h || 0, m || 0)
		},
		onClickDay(d) {
			this.selectionStart = null
			this.selectionEnd = null
			this.message = `날짜 : ${d.toLocaleDateString()}`
		},
		onClickItem(e) {
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
		clickTestAddItem() {
			console.log(this.newItemTitle)
			this.items.push({
				startDate: this.newItemStartDate,
				endDate: this.newItemEndDate,
				title: this.newItemTitle,
				id: "e" + Math.random().toString(36).substr(2, 10),
			})
			alert('일정을 추가했습니다.')
			// this.message = "You added a calendar item!"
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