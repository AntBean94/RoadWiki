<template>
    <b-card :title="roadmap.title">
    <Overview v-if="roadmapData" :roadmapData="roadmapData" id="overview" />
      <b-card-text>
        {{ username }}
      </b-card-text>
      <template #footer>
        <small class="text-muted">{{ roadmap.createDate }}</small>
      </template>
    </b-card>
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
      console.log(`${this.$store.getters.getServer}/roadmap/get/${this.roadmap.rmid}`)
      axios.get(`${this.$store.getters.getServer}/roadmap/get/${this.roadmap.rmid}`)
        .then((res) => {
          if(res.data.msg == 'success'){
            console.log(res);
            this.roadmapData = JSON.parse(res.data['roadmaps'].tmp);
            console.log('---------------------------------------',this.roadmapData);
          }else{
            alert("데이터 로드에 실패했습니다.");
          }
        }).catch((e) =>{
          console.log(e)
          alert("axios 오류")
        });
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