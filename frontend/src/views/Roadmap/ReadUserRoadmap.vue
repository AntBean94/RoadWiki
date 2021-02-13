<template>

  <div>
    <!-- header 시작 -->
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    <!-- carousel도 컴포넌트화 필요 -->
      <!-- Card stats -->
      <br>
        <carousel :per-page="4" :mouse-drag="true">
          <slide v-for="(item, index) in userRoadmapList" :key="index" >
            <b-col @click="previewRoadmap(item.rmorder, item.rmid)">
                  <stats-card type="gradient-red"
                            :sub-title="item.name"
                            icon="ni ni-active-40"
                            class="mb-4 btn" 
                            :rmid="item.rmid"
                            :rmorder="item.rmorder"
                            >
                  <template slot="footer">
                    <span class="text-success mr-2">{{ item.createDate }}</span>
                  </template>
                </stats-card>
                </b-col>
          </slide>
        </carousel>
    </base-header>
    <!-- header 종료 -->
    <!-- 다이어그램 container -->
    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <div style="text-align: right;">
            <button class="btn" style="background-color: rgb(256, 256, 256);" @click="goToCreate">생성하기</button>
            <button class="btn" style="background-color: rgb(256, 256, 256);" @click="goToUpdate">수정하기</button>
          </div>
          <b-card no-body class="border-0">
            <div class="inline-block" style="width: 100%;">
              <!-- goJS/start-->
              <Roadmap :roadmapMode=roadmapMode :roadmapData=roadmapData />
              <!--goJs/end -->
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
    <!-- 다이어그램 container -->
  </div>
</template>
<script>
import Roadmap from '@/components/Roadmap/Roadmap.vue'
import UpdateUserRoadmap from '@/views/Roadmap/UpdateUserRoadmap'
import router from '@/routes/router'
import { Carousel, Slide } from 'vue-carousel';


export default {
  router,
  name: 'ReadUserRoadmap',
  components: {
    Roadmap,
    Carousel,
    Slide,  
    UpdateUserRoadmap,
  },
  data() {
    return {
      roadmapMode: 0,
      userRoadmapList: [],
      isSelectCard: false,
      rmid: 0,
      rmorder: 0,
      roadmapData: {},
    }
  },
  created() {
    // carousel로 띄울 roadmap 리스트 요청
    const uid = String(this.$store.getters.getUid)
    axios.get(`${this.$store.getters.getServer}/roadmap/list/${uid}`)
      .then((res) => {
        if(res.data.msg == 'success') {
          // 유저의 roadmaplist
          if(res.data['roadmaps'].length){
            this.userRoadmapList = res.data['roadmaps'];
            // 시작페이지에서 클릭없이 첫 번째 로드맵을 보여줄 수 있도록 저장
            this.previewRoadmap(this.userRoadmapList[0].rmorder,this.userRoadmapList[0].rmid)
          }
        } else {
          alert("데이터 로드에 실패했습니다.")
        }
      }).catch((e) =>{
        console.log(e);
        alert("axios 오류")
      });
  },
  mounted() {
  },
  computed: {},
  methods: {
    // 리스트
    previewRoadmap(clickrmorder,clickrmid) {
      this.isSelectCard = true;
      this.rmid = clickrmid;
      this.rmorder = clickrmorder;
        axios.get(`${this.$store.getters.getServer}/roadmap/get/${clickrmid}`)
        .then((res) => {
          if(res.data.msg == 'success'){
          this.roadmapData = JSON.parse(res.data['roadmaps'].tmp);
          // this.load: ismounted 사용여부 확인 후 지울 것
          this.load();
          }else{
            alert("데이터 로드에 실패했습니다.");
          }
        }).catch((e) =>{
          console.log(e)
          alert("axios 오류")
        });
    },
    // 외부 json파일 초기화면에 출력
    load() {
      // ismounted? 어디서 쓰이는지 확인
      this.ismounted = true
    },
    // updateuserroadmap을 create, update모두 가능하도록 변경(mode로 나눈다.)
    goToCreate() {
      this.$router.push({ name : 'update_user_roamdap', params: { rmid: 0, CUMode: 0 }})
    },
    goToUpdate() {
      this.$router.push({ name: 'update_user_roamdap', params: { rmid: this.rmid, rmorder: this.rmorder, CUMode: 1 }})
    },
  },
}
</script>

<style>

</style>