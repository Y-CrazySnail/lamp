<template>
  <div>
    <beautiful-chat
      :participants="participants"
      :onMessageWasSent="onMessageWasSent"
      :messageList="messageList"
      :newMessagesCount="newMessagesCount"
      :isOpen="isChatOpen"
      :close="closeChat"
      :open="openChat"
      :showEmoji="false"
      :showFile="true"
      :showEdition="false"
      :showDeletion="false"
      :showTypingIndicator="showTypingIndicator"
      :showLauncher="true"
      :showCloseButton="true"
      :colors="colors"
      :alwaysScrollToBottom="alwaysScrollToBottom"
      :disableUserListToggle="false"
      :messageStyling="messageStyling"
      @onType="handleOnType"
      @edit="editMessage"
    >
    </beautiful-chat>
  </div>
</template>

<script>
import { getUsername, getToken } from "@/utils/auth";
export default {
  name: "app",
  mounted() {
    this.username = getUsername();
    this.connectWebsocket();
  },
  data() {
    return {
      websocket: null,
      username: "",
      participants: [
        {
          id: "service@richcloud.biz",
          name: "张富贵",
          imageUrl:
            "https://ts3.cn.mm.bing.net/th?id=OIP-C.iNR59B1A_jMMJpZxsH8qGgHaHa&w=250&h=250&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2",
        },
      ],
      messageList: [],
      newMessagesCount: 0,
      isChatOpen: false,
      showTypingIndicator: "",
      colors: {
        header: {
          bg: "#4e8cff",
          text: "#ffffff",
        },
        launcher: {
          bg: "#4e8cff",
        },
        messageList: {
          bg: "#ffffff",
        },
        sentMessage: {
          bg: "#4e8cff",
          text: "#ffffff",
        },
        receivedMessage: {
          bg: "#eaeaea",
          text: "#222222",
        },
        userInput: {
          bg: "#f4f7f9",
          text: "#565867",
        },
      },
      alwaysScrollToBottom: true,
      messageStyling: true,
    };
  },
  methods: {
    connectWebsocket() {
      let websocket;
      if (typeof WebSocket === "undefined") {
        return;
      } else {
        let Base64 = require("js-base64").Base64;
        let url =
          "ws://192.168.50.248:8080/server/message/" +
          Base64.encode(this.username);
        websocket = new WebSocket(url);
        websocket.onopen = () => {
          this.websocket = websocket;
        };
        websocket.onmessage = (evt) => {
          let data = JSON.parse(evt.data);
          this.newMessagesCount = data.unreadCount + this.newMessagesCount;
          let messageList = data.zeroMessageList;
          this.messageList = this.messageList.concat(messageList);
          if (this.isChatOpen) {
            this.$store.dispatch("zero_message/readMessage", {});
          }
        };
        websocket.onerror = (evt) => {};
        websocket.onclose = (evt) => {};
      }
    },
    sendMessage(text) {
      if (text.length > 0) {
        this.newMessagesCount = this.isChatOpen
          ? this.newMessagesCount
          : this.newMessagesCount + 1;
        this.onMessageWasSent({
          author: "support",
          type: "text",
          data: { text },
        });
      }
    },
    onMessageWasSent(message) {
      this.messageList = [...this.messageList, message];
    },
    openChat() {
      this.$store.dispatch("zero_message/readMessage", {});
      this.isChatOpen = true;
      this.newMessagesCount = 0;
    },
    closeChat() {
      this.isChatOpen = false;
    },
    handleScrollToTop() {},
    handleOnType() {
      console.log("Emit typing event");
    },
    editMessage(message) {
      const m = this.messageList.find((m) => m.id === message.id);
      m.isEdited = true;
      m.data.text = message.data.text;
    },
  },
};
</script>