<template>
  <div class="p-3">
    <base-input>
      <textarea 
        class="form-control" 
        rows="3" 
        placeholder="댓글을 입력해주세요"
        v-model="content"
      >
      </textarea>
      <b-row align-h="end">
        <b-button variant="default" class="mt-2 mr-3" @click="sendComment">댓글 작성</b-button>
      </b-row>
    </base-input>
  </div>
</template>

<script>
export default {
  name: 'commentform',
  data() {
    return {
      content: '',
    }
  },
  props: ['pid'],
  methods: {
    sendComment() {
      let posting = {
        'pid': this.pid,
        'uid': this.$store.getters.getUid,
        'content': this.content,
      }
      axios
      .post(`${this.$store.getters.getServer}/freeboard/comment`, posting)
      .then((res) => {
        this.$emit('sendComment')
        this.content = ''
      })
      .catch((err) => {
        console.log(err)
      })
    },
  },
}
</script>

<style>

</style>