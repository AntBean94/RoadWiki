<template>
  <div>
       <!-- <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default"></base-header>  -->
      <div v-for="(chatInfo, index) in chattingList" v-bind:key="index" @click="ClickchattingRoom(chatInfo)">
      선택한 오픈채팅방
      {{ chatInfo.roomid }}
      {{ chatInfo.name }}
      <hr>
      </div>
      
  </div>
</template>

<script>
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

</style>