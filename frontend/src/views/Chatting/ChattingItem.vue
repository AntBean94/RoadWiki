<template>
  <div>
      <div v-bind:style="chattingStlye" 
           @click="ClickchattingRoom(chatInfo)"
           @mouseover="getShadow"
           @mouseout="deleteShadow">
        선택한 오픈채팅방
        {{ chatInfo.roomid }}
        {{ chatInfo.name }}
      </div>
  </div>
</template>

<script>
export default {
    name: "ChattingItem",
    props: {
        roomid: Number,
        name: String,
    },
    data() {
        return{
            chattingStlye: {
              display: "inline-block",
              width: "240px",
              height: "150px",
              margin: "24px",
              overflow: "hidden",
              backgroundColor: "white",
              boxShadow: "",
           }
        }
    }, 
    methods: {
        getShadow(){ 
          this.chattingStlye.boxShadow = "5px 5px 5px"
        },
        deleteShadow(){
          this.chattingStlye.boxShadow = ""
        },
        ClickchattingRoom(chatInfo) {
            this.$emit('get-roominfo', chatInfo)
            alert(`${chatInfo.name}에 입장합니다.`)
        },
    },
}
</script>

<style>

</style>