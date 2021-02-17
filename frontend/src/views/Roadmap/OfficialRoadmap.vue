<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
      <!-- Card stats -->
      <br />
      <carousel :per-page="4" :mouse-drag="true">
        <slide v-for="(item, index) in curriculumData" :key="index">
          <b-col @click="previewRoadmap(item.rmid)">
            <stats-card
              type="gradient-red"
              :sub-title="item.name"
              icon="ni ni-active-40"
              class="mb-4 btn"
              :rmid="item.rmid"
            >
              <template slot="footer">
                <span class="text-success mr-2">{{ item.createDate }}</span>
              </template>
            </stats-card>
          </b-col>
        </slide>
      </carousel>
    </base-header>

    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <div style="text-align : right;">
            <div v-if="uid < 0" style="display: inline; margin: 20px;">
              <router-link
                :to="{ name: 'admin' }"
                class="btn"
                style=" background-color:#F9F8F3"
                >데이터 추가하기</router-link
              >
            </div>
            <button
              class="btn"
              style="background-color: rgb(256, 256, 256);"
              @click="goToCreate"
            >
              내보내기
            </button>
          </div>
          <b-card no-body class="border-0">
            <div style="width: 100%; display: inline-block;">
              <Roadmap :roadmapMode="roadmapMode" :roadmapData="roadmapData" />
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
// flatpickr 오류 수정 필요!
import ReadUserRoadMap from "@/views/Roadmap/ReadUserRoadmap";
// import
import router from "@/routes/router";
import Roadmap from "@/components/Roadmap/Roadmap.vue";
export default {
  router,
  name: "",
  components: {
    ReadUserRoadMap,
    Roadmap
  },
  data() {
    return {
      uid: this.$store.getters.getUid,
      roadmapData: {},
      curriculumData: [],
      rmid: 0,
      memotext: "",
      roadmapMode: 0
    };
  },
  created() {
    // carousel로 띄울 officialroadmap 리스트 요청
    axios
      .get(`${this.$store.getters.getRoadmapServer}/roadmap/Official`)
      .then(res => {
        if (res.data.msg == "success") {
          // 유저의 roadmaplist
          if (res.data["roadmaps"].length) {
            this.curriculumData = res.data["roadmaps"];
            // 시작페이지에서 클릭없이 첫 번째 로드맵을 보여줄 수 있도록 저장
            this.previewRoadmap(this.curriculumData[0].rmid);
          }
        } else {
          alert("데이터 로드에 실패했습니다.");
        }
      })
      .catch(e => {
        console.log(e);
        alert("axios 오류");
      });
  },
  watch: {},
  computed: {},
  methods: {
    previewRoadmap(clickrmid) {
      this.rmid = clickrmid;
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmap/get/${clickrmid}`)
        .then(res => {
          if (res.data.msg == "success") {
            this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          console.log(e);
          alert("axios 오류");
        });
    },
    goToCreate() {
      this.$router.push({
        name: "로드맵 수정하기",
        params: { rmid: this.rmid, CUMode: 0 }
      });
    }
  }
};
</script>

<style>
.bntn {
  background-color: aquamarine;
}
</style>
