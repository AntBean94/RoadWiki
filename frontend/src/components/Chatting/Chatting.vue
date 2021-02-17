<template>
  <div style="height:100%">
    <header style="height:10%">
      <p style="display:inline-block">채팅방</p>
      <button @click="close" style="display:inline-block; float : right">
        X
      </button>
    </header>
    <div
      style="height:83%; border:black 1px solid; overflow-y: scroll;"
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
    <footer style="height:7%">
      <input type="text" v-model="sender" style="width:20%; height:100%" />
      <input
        type="textarea"
        v-model="message"
        v-on:keypress.enter="sendMsg"
        style="width:60%; height:100%"
      />
      <button type="button" @click="sendMsg" style="width:20%; height:100%">
        전송
      </button>
    </footer>
  </div>
</template>
<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import store from "@/store";

const SERVER_URL = store.getters.getChattingServer;

export default {
  props: {
    rid: String
  },
  data() {
    return {
      sender: "익명",
      message: "",
      messages: []
    };
  },
  computed: {
    roomid() {
      return this.rid;
    }
  },
  watch: {
    roomid: function(newVal, oldVal) {
      this.disconnect();
      this.message = "";
      this.messages = [];
      this.connect();
    }
  },
  created() {
    this.sender = store.getters.getName;
  },
  destroyed() {
    this.disconnect();
  },
  methods: {
    sendMsg: function() {
      if (this.roomid.length < 1) {
        alert("채팅방을 선택해주세요");
      } else if (this.message.length < 1) {
        alert("메시지를 입력해주세요");
      } else {
        var msg = {
          type: "TALK",
          roomid: this.roomid,
          sender: this.sender,
          msg: this.message
        };
        this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
        this.message = "";
      }
    },
    disconnect: function() {
      if (this.stompClient !== undefined) {
        this.stompClient.disconnect();
      }
    },
    connect: function() {
      var reconnect = 0;
      let socket = new SockJS(SERVER_URL + "/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        frame => {
          this.stompClient.subscribe("/sub/chat/room/" + this.roomid, res => {
            let jsonBody = JSON.parse(res.body);
            let m = {
              type: jsonBody.type,
              sender: jsonBody.sender,
              message: jsonBody.msg
            };
            this.messages.push(m);

            var container = this.$el.querySelector("#content");

            setTimeout(function() {
              container.scrollTop = container.scrollHeight;
            }, 1);
          });
          var msg = {
            type: "JOIN",
            roomid: this.roomid,
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
    close: function() {
      this.disconnect();
      this.$emit("close");
    }
  }
};
</script>
