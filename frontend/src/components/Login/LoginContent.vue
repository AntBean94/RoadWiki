<template>
  <div>
    <b-button @click="modalShow = !modalShow" variant="classic-blue">로그인</b-button>

    <b-modal v-model="modalShow" hide-footer hide-header centered>
      <!-- <template #modal-title class="text-center mx-auto">
        <div class="h1">로그인</div>
      </template> -->
      <div class="h1 text-center display-3 mb-4">
        <!-- <img src="/img/brand/logo_blue.png" alt="roadwiki" width="250rem;"> -->
        <img src="/img/brand/final_logo.png" alt="roadwiki" width="300rem;">
      </div>
      <base-input alternative
              class="mb-3"
              name="Email"
              prepend-icon="ni ni-email-83"
              placeholder="email@email.com"
              v-model="user.email"
              @keydown.enter="signIn"
      >
      </base-input>

      <base-input alternative
                  class="mb-3"
                  name="Password"
                  prepend-icon="ni ni-lock-circle-open"
                  type="password"
                  placeholder="Password"
                  v-model="user.password"
                  @keydown.enter="signIn"
      >
      </base-input>
      <div>
        <!-- 근데 이거 왜 필요한거지 -->
        <a href="">로그인이 안되시나요?</a>
      </div>
      <div class="text-center">
        <base-button block type="peach-quartz" native-type="submit" class="my-4" @click="signIn">로그인하기</base-button>
      </div>
    </b-modal>
  </div>
  <!-- <b-modal id="modal-login">
  </b-modal> -->
</template>

<script>
export default {
  data() {
    return {
      modalShow: false,
      user:{
        email: '',
        password: '',
      }
    }
  },
  methods: {
    signIn() {
      this.$store.dispatch("LOGIN", this.user).then(() => {
        this.$router.push('/dashboard')
      })
      .catch(() => {
        alert('아이디 비밀번호를 확인해주세요.')
        this.user.email = ''
        this.user.password = ''
      })
    },
  },
}
</script>

<style>

</style>