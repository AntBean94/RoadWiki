<template>
    <div>
        <div v-if="message" class="notification is-success">{{ message }}</div>
        <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default"></base-header>
        <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <b-card no-body class="border-0">
            <h1> {{ userName }} 님의 일정</h1>
            <calendar-view
                :show-date="showDate"
                class="theme-default holiday-us-traditional holiday-us-official">
                <calendar-view-header
                    slot="header"
                    slot-scope="t"
                    :header-props="t.headerProps"
                    @input="setShowDate" />
            </calendar-view>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
        
    </div>
    
</template>

<script>

import { CalendarView, CalendarViewHeader } from "vue-simple-calendar"
    require("vue-simple-calendar/static/css/default.css")
    require("vue-simple-calendar/static/css/holidays-us.css")

    export default {
        name: 'app',
        data: function() {
            return { 
                showDate: new Date(),
                userName: "",
                message: "",
            }
        },
        mounted() {
            // 사용자 이름 올리기
            this.userName = this.$store.getters.getName

            // 
        },
        components: {
            CalendarView,
            CalendarViewHeader,
        },
        methods: {
            setShowDate(d) {
                this.showDate = d;
            },
        }
    }
</script>

<style scoped>

    
</style>