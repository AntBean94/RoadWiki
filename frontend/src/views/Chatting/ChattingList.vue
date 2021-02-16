<template>
  <div>
       <!-- <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default"></base-header>  -->
      <div id="chatting-list" v-for="(chatInfo, index) in chattingList" v-bind:key="index" @click="ClickchattingRoom(chatInfo)">
      선택한 오픈채팅방
      {{ chatInfo.roomid }}
      {{ chatInfo.name }}
      
      </div>
      
  </div>
</template>

<script>
import store from "@/store";

const SERVER_URL = store.getters.getServer;

export default {
    name: "Chatting",
    props: {
        roomid: Number,
        name: String, 
    },
    data() {
        return {
           chattingList: [],
        }
    },
    created(){
        this.getRooms()
    },
    methods: {
        ClickchattingRoom(chatInfo) {
            this.$emit('get-roominfo', chatInfo)
            alert(`${chatInfo.name}에 입장합니다.`)
        },
        getRooms: function() {
         axios
        .get(SERVER_URL + "/chat/open")
        .then(response => {
          console.log(response)
          this.chattingList = response.data.chatRooms;
          
        })
        .catch(response => {
          console.log(response);
        });
    },
    },
}
</script>

<style>
#chatting-list{
  display: inline-block;
  width: 240px;
  height: 300px;
  margin: 24px; 
  overflow: hidden;
  background-color: white;
  box-shadow: 5px 5px 5px;
}
</style>