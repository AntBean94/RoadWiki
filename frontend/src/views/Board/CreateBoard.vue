<template>
  <div class='d-flex flex-column'>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>


    <div class="p-4 bg-secondary">
      <b-input placeholder="제목" class="form-control-alternative"/>
    </div>

    <div>
      <b-card-form>
        <editor
          ref="toastuiEditor"
          :initialValue="editorText"
          :options="editorOptions"
          height="500px"
          initialEditType="wysiwyg"
          previewStyle="vertical"
          class="mx-4"
        />
      </b-card-form>
    </div>

    <b-col cols="12">
      <v-autocomplete
        v-model="values"
        :items="items"
        dense
        chips
        small-chips
        label="Solo"
        multiple
        solo
      >
      </v-autocomplete>
    </b-col>

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
      editorText: '내용을 입력해주세요',
      editorOptions: {
        hideModeSwitch: false
      },
    };
  },
  methods: {
    datachange() {
      const content = this.$refs.toastuiEditor.invoke("getMarkdown");
      
      this.editorText = content

      console.log(this.$refs.toastuiViewer)
      
      this.$refs.toastuiViewer.editor.markdownValue = content
      // console.log(this.editorText)
      // this.editorText = (Editor.getMarkdown())
      
    }
  },
}
</script>

<style>

</style>