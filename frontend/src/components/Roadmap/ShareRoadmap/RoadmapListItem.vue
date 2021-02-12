<template>
  <div @click="goDetail">
    <b-card :title="roadmap.title">
    <Overview v-if="roadmapData" :roadmapData="roadmapData" id="overview" />
      <b-card-text>
        {{ username }}
      </b-card-text>
      <template #footer>
        <small class="text-muted">{{ roadmap.createDate }}</small>
        <br>
        <small class="text-muted">좋아요 : {{ roadmap.likecnt }}</small>
      </template>
    </b-card>
</div>
</template>
<script>
import Overview from '@/components/Roadmap/Overview.vue'
export default {
  components: {
    Overview,
  },
  props: ["roadmap","username"],
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
            alert("데이터 로드에 실패했습니다.");
          }
        }).catch((e) =>{
          console.log(e)
          alert("axios 오류")
        });
    },
  goDetail() {
      this.$router.push({name: 'sharedetail' , params: { roadmap: this.roadmap,uname: this.username }})
    },
  },
  created(){
    this.getRoadmapData();
  }
}
</script>

<style>
#overview {
  width: 150px;
  height: 150px;
}
</style>