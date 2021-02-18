<template>
  <div style="height:100%">
    <header style="height:5%"></header>
    <div style="height:95%">
      <div
        v-for="(room, idx) in rooms"
        :key="idx"
        :value="room.id"
        class="rcomp"
        style="width:94%; border:black solid 1px; margin:0% 3%;"
        @click="
          selRoom($event);
          sendRoom(room);
        "
      >
        <span>{{ room.name }}</span>
      </div>
    </div>
  </div>
</template>
<script>
import store from "@/store";

const SERVER_URL = store.getters.getChattingServer;

export default {
  data() {
    return {
      fields: ["name"],
      rooms: [],
      roomid: "",
      selComp: null
    };
  },
  methods: {
    getRooms: function() {
      axios
        .get(SERVER_URL + "/chat/open")
        .then(response => {
          console.log(response);
          this.rooms = response.data.chatRooms;
          console.log(this.rooms);
        })
        .catch(response => {
          console.log(response);
        });
    },
    selRoom: function(event) {
      if (this.selComp != null) this.selComp.id = "rcomp";
      event.target.id = "selrow";
      this.selComp = event.target;
    },
    sendRoom: function(room) {
      this.$emit("selectRoom", room.roomid);
      // sessionStorage.setItem("roomidSc", this.roomid);
    }
  },
  created() {
    this.getRooms();
  }
};
</script>
<style scoped>
#selrow {
  background-color: skyblue;
}
</style>
