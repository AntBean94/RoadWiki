<template>
  <div class="d-flex flex-column">
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>

    <div class="p-4 bg-secondary">
      <b-input
        v-if="title"
        placeholder="제목"
        class="form-control-alternative"
        v-model="title"
      />
    </div>

    <div>
      <editor
        v-if="content"
        ref="toastuiEditor"
        :options="editorOptions"
        height="500px"
        initialEditType="wysiwyg"
        previewStyle="vertical"
        class="mx-4"
        :initialValue="content"
        id="editor"
      />
    </div>

    <b-container class="mt-3">
      <b-row class="mx-2">
        <div
          class="ml-1 mt-2 mr-1 bg-warning rounded-pill py-1 px-2"
          v-for="(tag, idx) in tags"
          :key="idx"
        >
          <span class="h5"># {{ tag }}</span>
          <span class="ml-2 h5" style="color:black" @click="delTag(idx)">
            <i class="fas fa-times"></i>
          </span>
        </div>
      </b-row>
    </b-container>

    <div class="p-4 bg-secondary">
      <b-input
        placeholder="태그를 추가해주세요"
        class="form-control-alternative"
        @keydown.enter="tagEnter"
        @blur="tagEnter"
        v-model="tagInput"
      />
    </div>

    <b-button @click="datachange" class="mt-3 mx-4" variant="default">
      저장
    </b-button>
  </div>
</template>

<script>
import "@toast-ui/editor/dist/toastui-editor.css";
import "codemirror/lib/codemirror.css";
import { Editor } from "@toast-ui/vue-editor";
import store from "@/store";
import routes from "@/routes/routes";

// local 이 아니라 this.$store.getters.getBoardServer 73이아니라 파람으로 받아온거$route.param
// axios.get(`http://localhost:8085/freeboard/posting/73`)

// console.log(routes.query.pid)

export default {
  name: "Editor",
  components: {
    editor: Editor
  },
  data() {
    return {
      content: "",
      editorOptions: {
        hideModeSwitch: false
      },
      tags: ["첫번째 태그", "두번째 태그", "세번째 태그", "네번째 태그"],
      tagInput: "",
      title: ""
    };
  },
  methods: {
    getOriginPosting() {
      axios
        .get(
          `${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`
        )
        .then(res => {
          console.log("origin data");
          console.log(res.data);
          this.content = res.data.posting.content;
          this.title = res.data.posting.title;
          console.log("여기는 title");
          console.log(this.title);
        });
    },
    datachange() {
      const content = this.$refs.toastuiEditor.invoke("getMarkdown");
      const pid = this.$route.query.pid;
      console.log('###################')
      console.log(content)
      let posting = {
        title: this.title,
        content: content,
        pid: pid,
      }
      axios
        .put(`${this.$store.getters.getBoardServer}/freeboard/posting`, posting)
        .then(() => {
          alert('글 작성이 완료되었습니다.')
          this.$router.push({name: '게시글', query: { pid: pid }})
        });
    },
    tagEnter() {
      if (this.tagInput) {
        this.tags.push(this.tagInput);
        this.tagInput = "";
      }
    },
    delTag(idx) {
      this.tags.splice(idx, 1);
    },
    editorLoading() {
      axios
        .get(
          `${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`
        )
        .then(res => {
          this.content = res.data.posting.content;
        })
        .finally(() => {});
    }
    // onEditorLoad() {
    //   axios.get(`${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`)
    //   .then((res) => {
    //     console.log(res.data.posting)
    //     this.name = res.data.name
    //     this.classifier = res.data.posting.classifier
    //     this.title = res.data.posting.title
    //     this.content = res.data.posting.content
    //     this.createDate = res.data.posting.createDate
    //     this.modifyDate = res.data.posting.modifyDate
    //     this.uid = res.data.posting.uid
    //     this.likeCnt = res.data.posting.likeCnt
    //   })
    // },
  },
  watch: {
    content: function() {
      console.log("바뀌는중...");
    }
  },
  created() {
    console.log(
      `${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`
    );
    this.getOriginPosting();
    this.editorLoading();
  }
};
</script>

<style></style>
