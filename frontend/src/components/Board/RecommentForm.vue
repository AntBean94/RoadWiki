<template>
  <div>
    <!-- <hr class="mb-2 mt-0"> -->
    <base-input>
      <textarea 
        class="form-control mt-2" 
        rows="2" 
        placeholder="댓글을 입력해주세요"
        v-model="comment"
      >
      </textarea>
      <b-row align-h="end">
        <b-button 
          variant="default" 
          class="mt-2 mr-3" 
          @click="sendComment"
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
      comment: '',
    }
  },
  // props: ['pid'],
  methods: {
    sendComment() {
    },
  },
}
</script>

<style>

</style>