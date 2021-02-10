<template>
  <div class="bg-secondary p-4 mx-3">
    <hr class="my-2">
    <base-input>
      <textarea 
        class="form-control mt-2" 
        rows="2" 
        placeholder="댓글을 입력해주세요"
        v-model="content"
      >
      </textarea>
      <b-row align-h="end">
        <b-button 
          variant="default" 
          class="mt-2 mr-3" 
          @click="sendRecomment"
          size="sm"
        >
          등록
        </b-button>
      </b-row>
    </base-input>
  </div>
</template>

<script>
export default {
  name: 'recommentform',
  data() {
    return {
      content: '',
    }
  },
  props: ['cid'],
  methods: {
    sendRecomment() {
      let recomment = {
        'cid': this.cid,
        'content': this.content,
      }
      axios.post(`${this.$store.getters.getServer}/freeboard/recomment`, recomment)
      .then((res) => {
        // alert('대댓글이 작성되었습니다.')
        this.content = ''
        this.$emit('sendRecomment')
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