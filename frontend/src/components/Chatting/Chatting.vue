<template>
  <div>
    <header></header>
    <div style="height:450px">
      <p>채팅방</p>
      <ul>
        <li v-for="(message, idx) in messages" v-bind:key="idx">
          {{ message.sender }} >> {{ message.message }}
        </li>
      </ul>
    </div>
    <footer>
      <input type="text" v-model="sender" style="width:70px" />
      <input
        type="text"
        v-model="message"
        v-on:keypress.enter="sendMsg"
        style="width:170px"
      />
      <button type="button" @click="sendMsg">전송</button>
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
      messages: [
        {
          sender: "시작",
          message: "메시지"
        }
      ]
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
      console.log("Send msg : ", sessionStorage.getItem("roomid"));
      var msg = {
        type: "TALK",
        roomid: sessionStorage.getItem("roomid"),
        sender: this.sender,
        message: this.message
      };
      this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
    },
    connect: function() {
      let socket = new SockJS(SERVER_URL + "/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        frame => {
          console.log(
            "구독 : /sub/chat/room/" + sessionStorage.getItem("roomid")
          );
          this.stompClient.subscribe(
            "/sub/chat/room/" + sessionStorage.getItem("roomid"),
            res => {
              console.log("구독성공");
              console.log(response);
              let jsonBody = JSON.parse(res.data);
              console.log(jsonBody);
              let m = {
                type: "JOIN",
                roomid: sessionStorage.getItem("roomid"),
                sender: this.sender,
                message: this.message
              };
              this.messages.push(jsonBody);
            }
          );
        },
        error => {
          console.log(error);
        }
      );
    }

    // connect: function() {
    //   console.log("------------- connect 시작 ----------");
    //   let sock = new SockJS(SERVER_URL + "/ws");
    //   this.stompClient = Stomp.over(function() {
    //     return sock;
    //   });
    //   var msg = {
    //     type: "JOIN",
    //     roomid: sessionStorage.getItem("roomid"),
    //     sender: this.sender,
    //     message: this.message
    //   };
    //   this.stompClient.connect(
    //     {},
    //     frame => {
    //       console.log("-------------구독 출력-----------");
    //       console.log(msg);
    //       console.log(sessionStorage.getItem("roomid"));
    //       this.stompClient.subscribe(
    //         "/sub/chat/room/" + sessionStorage.getItem("roomid"),
    //         result => {
    //           sessionStorage.getItem("roomid");
    //           console.log(result);
    //           var recv = JSON.parse(result.body);
    //           this.recvMsg(recv);
    //           console.log("메시지 body = ", recv);
    //           this.stompClient.send(
    //             "/pub/chat/message",
    //             {},
    //             JSON.stringify(msg)
    //           );
    //         },
    //         error => {
    //           console.log("fail", err);
    //         }
    //       );
    //     },
    //     function(error) {
    //       if (reconnect++ <= 5) {
    //         setTimeout(function() {
    //           console.log("connection reconnect");
    //           let sock = new SockJS(SERVER_URL + "/ws");
    //           this.stompClient = Stomp.over(function() {
    //             return sock;
    //           });
    //           connect();
    //         }, 10 * 1000);
    //       }
    //     }
    // );
    // }
  }
};
</script>
<style></style>
