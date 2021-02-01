<template>
  <div>
    <!-- <b-button variant="default" size="sm" @click="modalShow = !modalShow">수정하기</b-button> -->
    <b-button variant="default" size="sm" @click="clickBtn">수정하기</b-button>

    <b-modal v-model="modalShow" hide-footer>
      <template #modal-title>
        <h1>관심 개발 분야</h1>
      </template>
      <div>
        <b-form-group label="" v-slot="{ ariaDescribedby }">
          <p>가장 관심있는 것부터 순서대로 체크해주세요. (3개 이상 선택 가능)</p>
          <b-container>
            <!-- {{selected.length}} -->
            <!-- {{selected}} -->
            <b-form-checkbox
              v-for="option in options"
              v-model="selected"
              :key="option.kwid"
              :value="option.kwid"
              :aria-describedby="ariaDescribedby"
              name="flavour-3a"
            >
              {{ option.word }}
            </b-form-checkbox>
            <hr>
            <b-row class="justify-content-md-center">
              <b-col class="ml-3">1순위</b-col> 
              <b-col v-if="selected.length > 0">{{ options[selected[0]-1].word }}</b-col>
            </b-row>
            <b-row class="justify-content-md-center">
              <b-col class="ml-3">2순위</b-col> 
              <b-col v-if="selected.length > 1">{{ options[selected[1]-1].word }}</b-col>
            </b-row>
            <b-row class="justify-content-md-center">
              <b-col class="ml-3">3순위</b-col> 
              <b-col v-if="selected.length > 2">{{ options[selected[2]-1].word }}</b-col>
            </b-row>
          </b-container>
        </b-form-group>
      </div>
      <div class="text-center">
        <base-button type="primary" native-type="submit" class="my-4" @click="sendFlavour">확인</base-button>
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
      selected: [],
      options: [],
    }
  },
  created() {
    axios.get(`${this.$store.getters.getServer}/keyword/list`)
    .then((res) => {
      this.options = res.data.keywords
    })
  },
  watch: {
    selected() {
      console.log(this.selected)
    }
  },
  // computed: {
  //   selectedNum() {

  //   },
  // },
  methods: {
    clickBtn() {
      this.modalShow = true
      axios
        .get(`${this.$store.getters.getServer}/user/info`)
        .then((res) => {
          console.log('여기는 selected')
          console.log(this.selected)
          console.log('이거는 keywords')
          console.log(res.data.keywords)
          console.log('이거는 data')
          console.log(res.data)
          this.selected = res.data.keywords;
          console.log(this.selected)
        })
        .catch(() => {
          alert("로그인이 필요한 서비스입니다.")
          this.$store.dispatch("LOGOUT").then(() => {
            this.$router.replace("/")
          })
        })

    },
    sendFlavour() {
      this.modalShow = false
      this.$emit('changeFlavour', this.selected)
    },
  },
}
</script>

<style>

</style>