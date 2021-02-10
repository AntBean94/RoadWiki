<template>
    <b-card title="Title" img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
      <b-card-text >
        This is a wider card with supporting text below as a natural lead-in to additional content.
        This content is a little bit longer.
      </b-card-text>
      <template #footer>
        <small class="text-muted">Last updated 3 mins ago</small>
      </template>
    </b-card>
</template>

<script>
import Overview from '@/components/Roadmap/Overview.vue'
export default {
  components: {
    Overview,
  },
  props: ["roadmap"],
  data() {
    return {
      roadmapData: "",
    }
  },
  methods: {
    getRoadmapData() {
      // rmid활용하여 로드맵 데이터 불러오기
      axios.get(`${this.$store.getters.getServer}/roadmap/get/${this.roadmap.rmid}`)
        .then((res) => {
          if(res.data.msg == 'success'){
            this.roadmapData = JSON.parse(res.data['roadmaps'].tmp);
          }else{
            console.log(res);
            alert("데이터 로드에 실패했습니다.");
          }
        }).catch((e) =>{
          console.log(e)
          alert("axios 오류")
        });
    },
  },
  created(){
    console.log("in roadmap item", this.roadmap);
  }
}
</script>

<style>

</style>