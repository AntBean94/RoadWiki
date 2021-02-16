<template>

  <div>
    <!-- header 시작 -->
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-baby-blue">
    <!-- carousel도 컴포넌트화 필요 -->
      <!-- Card stats -->
      <br>
        <carousel :per-page="4" :mouse-drag="true">
          <slide v-for="(item, index) in userRoadmapList" :key="index" >
            <b-col @click="previewRoadmap(item.rmorder, item.rmid)" id="carouselCard" >
              
              <b-card 
                id="carouselCard"
                class="text-center"
              >
              <!-- 장기 -->
              <div v-if="item.term == 1">
                <b-card-body class="border border-traffic-green rounded p-3 cardboarder" align="center"
                style="border: solid 10px;">
                  <b-card-title class="h3">
                    {{ item.name }}
                  </b-card-title> 
                  <b-card-text class="small text-muted text-center">
                    {{ item.createDate }}
                  </b-card-text>
                </b-card-body>
              </div>

              <!-- 중기 -->
              <div v-else-if="item.term == 2">
                <b-card-body class="border border-traffic-yellow rounded p-3" align="center">
                  <b-card-title class="h3">
                    {{ item.name }}
                  </b-card-title> 
                  <b-card-text class="small text-muted text-center">
                    {{ item.createDate }}
                  </b-card-text>
                </b-card-body>
              </div>

              <!-- 단기 -->
              <div v-else>
                <b-card-body class="border border-traffic-red rounded p-3" align="center">
                  <b-card-title class="h3">
                    {{ item.name }}
                  </b-card-title> 
                  <b-card-text class="small text-muted text-center">
                    {{ item.createDate }}
                  </b-card-text>
                </b-card-body>
              </div>
              </b-card>

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
            <b-button class="mb-3 mr-2" @click="goToCreate" variant="cornhusk">생성하기</b-button>
            <b-button class="mb-3 ml-2" @click="goToUpdate" variant="classic-blue">수정하기</b-button>
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


let roadmapDataTemp = { "class": "go.GraphLinksModel",
  "linkFromPortIdProperty": "fromPort",
  "linkToPortIdProperty": "toPort",
  "nodeDataArray": [
{"category":"Comment", "loc":"360 -10", "text":"Kookie Brittle", "key":-13},
{"key":-1, "category":"Start", "loc":"175 0", "text":"Start"},
{"key":0, "loc":"-5 75", "text":"Preheat oven to 375 F"},
{"key":1, "loc":"175 100", "text":"In a bowl, blend: 1 cup margarine, 1.5 teaspoon vanilla, 1 teaspoon salt"},
{"key":2, "loc":"175 200", "text":"Gradually beat in 1 cup sugar and 2 cups sifted flour"},
{"key":3, "loc":"175 290", "text":"Mix in 6 oz (1 cup) Nestle's Semi-Sweet Chocolate Morsels"},
{"key":4, "loc":"175 380", "text":"Press evenly into ungreased 15x10x1 pan"},
{"key":5, "loc":"355 85", "text":"Finely chop 1/2 cup of your choice of nuts"},
{"key":6, "loc":"175 450", "text":"Sprinkle nuts on top"},
{"key":7, "loc":"175 515", "text":"Bake for 25 minutes and let cool"},
{"key":8, "loc":"175 585", "text":"Cut into rectangular grid"},
{"key":-2, "category":"End", "loc":"175 660", "text":"Enjoy!"}
 ],
  "linkDataArray": [
{"from":1, "to":2, "fromPort":"B", "toPort":"T"},
{"from":2, "to":3, "fromPort":"B", "toPort":"T"},
{"from":3, "to":4, "fromPort":"B", "toPort":"T"},
{"from":4, "to":6, "fromPort":"B", "toPort":"T"},
{"from":6, "to":7, "fromPort":"B", "toPort":"T"},
{"from":7, "to":8, "fromPort":"B", "toPort":"T"},
{"from":8, "to":-2, "fromPort":"B", "toPort":"T"},
{"from":-1, "to":0, "fromPort":"B", "toPort":"T"},
{"from":-1, "to":1, "fromPort":"B", "toPort":"T"},
{"from":-1, "to":5, "fromPort":"B", "toPort":"T"},
{"from":5, "to":4, "fromPort":"B", "toPort":"T"},
{"from":0, "to":4, "fromPort":"B", "toPort":"T"}
 ]}

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
      userRoadmapList: [{"name": "안녕안녕안녕안녕안안녕안녕안녕안녕안안녕안녕안녕안녕안", "term": 1, "createDate": "2020-12-12", "rmid": 1, "rmorder": 1 , "tmp": roadmapDataTemp }, 
      {"name": "안녕2", "term": 3, "createDate": "2020-12-12", "rmid": 2, "rmorder": 2, "tmp": roadmapDataTemp}, 
      {"name": "안녕3", "term": 2, "createDate": "2020-12-12", "rmid": 3, "rmorder": 3, "tmp": roadmapDataTemp}],
      isSelectCard: false,
      rmid: 0,
      rmorder: 0,
      roadmapData: roadmapDataTemp,
    }
  },
  created() {
    // carousel로 띄울 roadmap 리스트 요청
    // const uid = String(this.$store.getters.getUid)
    // axios.get(`${this.$store.getters.getServer}/roadmap/list/${uid}`)
    //   .then((res) => {
    //     console.log(res);
    //     if(res.data.msg == 'success') {
    //       // 유저의 roadmaplist
    //       if(res.data['roadmaps'].length){
    //         this.userRoadmapList = res.data['roadmaps'];
    //         // 시작페이지에서 클릭없이 첫 번째 로드맵을 보여줄 수 있도록 저장
    //         this.previewRoadmap(this.userRoadmapList[0].rmorder,this.userRoadmapList[0].rmid)
    //       }
    //     } else {
    //       alert("데이터 로드에 실패했습니다.")
    //     }
    //   }).catch((e) =>{
    //     console.log(e);
    //     alert("axios 오류")
    //   });
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
      this.$router.push({ name : '로드맵 수정하기', params: { rmid: 0, CUMode: 0 }})
    },
    goToUpdate() {
      this.$router.push({ name: '로드맵 수정하기', params: { rmid: this.rmid, rmorder: this.rmorder, CUMode: 1 }})
    },
  },
}
</script>

<style>
/* .roadmap {
  width: 175px;
  height: 140;
  border-width: 10px;
  border-style: solid;
} */
</style>