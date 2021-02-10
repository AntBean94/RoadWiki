<template>
  <div id="chatting">
    <header style="height:10%">
      <p style="display:inline-block">채팅방</p>
      <button @click="removeChat" style="display:inline-block; float : right">
        X
      </button>
    </header>
    <div
      style="height:80%; border:black 1px solid; overflow: scroll;"
      id="content"
    >
      <!-- <ul style="margin:0px"> -->
      <li
        v-for="(message, idx) in messages"
        v-bind:key="idx"
        style="margin:0px; font-size:14px"
      >
        {{ message.sender }} >> {{ message.message }}
      </li>
      <!-- </ul> -->
    </div>
    <footer style="height:10%">
      <input type="text" v-model="sender" style="width:20%" />
      <input
        type="text"
        v-model="message"
        v-on:keypress.enter="sendMsg"
        style="width:60%"
      />
      <button type="button" @click="sendMsg" style="width:20%">전송</button>
    </footer>
  </div>
</template>
<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import store from "@/store";

const SERVER_URL = store.getters.getServer;

export default {
  data() {
    return {
      sender: "익명",
      message: "",
      messages: []
    };
  },
  created() {
    this.getRoom();
  },
  methods: {
    getRoom: function() {
      axios
        .get(SERVER_URL + "/chat/room")
        .then(response => {
          this.room = response.data.chatRoom;
          sessionStorage.setItem("roomid", response.data.chatRoom.roomid);
          this.connect();
        })
        .catch(response => {
          console.log(response);
        });
    },
    sendMsg: function() {
      var msg = {
        type: "TALK",
        roomid: sessionStorage.getItem("roomid"),
        sender: this.sender,
        msg: this.message
      };
      this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
    },
    connect: function() {
      var reconnect = 0;
      let socket = new SockJS(SERVER_URL + "/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        frame => {
          this.stompClient.subscribe(
            "/sub/chat/room/" + sessionStorage.getItem("roomid"),
            res => {
              let jsonBody = JSON.parse(res.body);
              let m = {
                type: jsonBody.type,
                sender: jsonBody.sender,
                message: jsonBody.msg
              };
              this.messages.push(m);
              this.message = "";
              var container = this.$el.querySelector("#content");
              setTimeout(function() {
                container.scrollTop = container.scrollHeight;
              }, 1);
            }
          );
          var msg = {
            type: "JOIN",
            roomid: sessionStorage.getItem("roomid"),
            sender: this.sender,
            msg: this.message
          };
          this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
        },
        error => {
          if (reconnect++ <= 5) {
            setTimeout(function() {
              console.log("connection reconnect");
              let sock = new SockJS(SERVER_URL + "/ws");
              this.stompClient = Stomp.over(function() {
                return sock;
              });
              connect();
            }, 10 * 1000);
          }
        }
      );
    },
    removeChat: function() {
      this.$emit("remove");
    }
  }
};
</script>
<style>
#chatting {
  position: fixed;
  bottom: 5px;
  right: 5px;
  width: 300px;
  height: 500px;
  background-color: whitesmoke;
  border: 1px black solid;
  z-index: 1000;
}
</style>
