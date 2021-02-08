<template>
  <div class='d-flex flex-column'>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>

    <div class="p-4 bg-secondary">
      <b-input 
        placeholder="제목" 
        class="form-control-alternative"
        v-model="title"
      />
    </div>

    <div>
      <editor
        ref="toastuiEditor"
        :initialValue="initialText"
        :options="editorOptions"
        height="500px"
        initialEditType="wysiwyg"
        previewStyle="vertical"
        class="mx-4"
        v-model="editorText"
      />
    </div>

    <b-container class="mt-3">
      <b-row class="mx-2">
        <!-- <b-badge
          pill
          class="ml-1 badge-md mt-2 mr-1" 
          variant="warning"
          v-for="(tag, idx) in tags"
          :key="idx"
        >
          <span class="h5">{{ tag }}</span>
          <span 
            class="ml-2 h5" 
            style="color:black"
            @click="delTag(idx)"
          >
            <i class="fas fa-times"></i>
          </span>

        </b-badge> -->
        <div
          class="ml-1 mt-2 mr-1 bg-warning rounded-pill py-1 px-2" 
          v-for="(tag, idx) in tags"
          :key="idx"
        >
          <span class="h5"># {{ tag }}</span>
          <span 
            class="ml-2 h5" 
            style="color:black"
            @click="delTag(idx)"
          >
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

    <b-button
      @click="datachange"
      class="mt-3 mx-4"
      variant="default"
    >
      저장
    </b-button>
  </div>
</template>

<script>
import '@toast-ui/editor/dist/toastui-editor.css'
import 'codemirror/lib/codemirror.css'
import { Editor } from '@toast-ui/vue-editor'

export default {
  name: 'Editor',
    components: {
    'editor': Editor,
  },
  data() {
    return {
      initialText: '내용을 입력해주세요',
      editorOptions: {
        hideModeSwitch: false,
      },
      editorText: '',
      tags: ['첫번째 태그', '두번째 태그', '세번째 태그', '네번째 태그'],
      tagInput: '',
      title: '',
    };
  },
  methods: {
    datachange() {

      const content = this.$refs.toastuiEditor.invoke("getMarkdown");
      // console.log(typeof(content))
      axios
      .post(`${this.$store.getters.getServer}/freeboard/posting`, 
      { 
        'title': this.title, 
        'content': content
      })
      .then(() => {})
      // this.editorText = content
      // this.$refs.toastuiViewer.editor.markdownValue = content
    },
    tagEnter() {
      console.log('엔터 찍힘')
      if (this.tagInput) {
        this.tags.push(this.tagInput)
        this.tagInput = ''
      }
    },
    delTag(idx) {
      this.tags.splice(idx, 1);
    },
  },
  // watch: {
  //   editorText() {
  //     console.log(this.editorText)
  //   }
  // },
}
</script>

<style>

</style>