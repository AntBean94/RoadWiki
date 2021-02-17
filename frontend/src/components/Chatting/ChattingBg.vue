<template>
  <div id="chatting">
    <b-container style="height:100%; width:100%; padding:0px">
      <b-row style="height:100%; width:100%; margin:0px">
        <b-col cols="4" style="padding:0px">
          <chatting-list v-on:selectRoom="changeChat" />
        </b-col>
        <b-col cols="8" style="border-left:1px solid black; padding:0px">
          <chatting
            v-on:close="removeChat"
            :rid="roomid"
            style="height:500px"
          />
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import Chatting from "@/components/Chatting/Chatting";
import ChattingList from "@/components/Chatting/ChattingList";

export default {
  components: {
    Chatting,
    ChattingList
  },
  data() {
    return {
      roomid: ""
    };
  },
  methods: {
    removeChat: function() {
      this.$emit("remove");
    },
    changeChat: function(roomid) {
      this.roomid = roomid;
    }
  },
  created() {}
};
</script>

<style scoped>
#chatting {
  position: fixed;
  bottom: 5px;
  right: 5px;
  width: 500px;
  height: 500px;
  background-color: whitesmoke;
  border: 1px black solid;
  z-index: 1000;
}
</style>
