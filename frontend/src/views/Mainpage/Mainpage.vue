<template>
  <div>
    <b-container>
      <b-carousel
        id="carousel-1"
        v-model="slide"
        :interval="60000"
        controls
        indicators
        background="#ababab"
        img-width="1024"
        img-height="480"
        style="text-shadow: 1px 1px 2px #333;"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <!-- 인기 로드맵 2개 -->
        <b-carousel-slide v-if="roadmapsharesF">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=183"
              alt="image slot"
              style="filter: brightness(0.5)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8)">Highly mentioned Roadmap</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ roadmapsharesF.title }} |</span> {{ roadmapsharesF.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">-</h4>
        </b-carousel-slide>

        <b-carousel-slide v-if="roadmapsharesS">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=186"
              alt="image slot"
              style="filter: brightness(0.5)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8)">Highly mentioned Roadmap</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ roadmapsharesS.title }} |</span> {{ roadmapsharesS.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">-</h4>
        </b-carousel-slide>

        <!-- 인기 게시글 2개 -->
        <b-carousel-slide v-if="postF">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=20"
              alt="image slot"
              style="filter: brightness(0.5)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8);">Popular post</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ postF.title }} |</span> {{ postF.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">{{ postF.content }}</h4>
        </b-carousel-slide>

        <b-carousel-slide v-if="postS">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=366"
              alt="image slot"
              style="filter: brightness(0.5)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8);">Popular post</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ postS.title }} |</span> {{ postS.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">{{ postS.content }}</h4>
        </b-carousel-slide>

        <!-- 오피셜 로드맵(싸피, 개발자) -->
        <b-carousel-slide v-for="(official, idx) in officials" :key="idx">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              :src=pictureList[idx]
              alt="image slot"
              style="filter: brightness(0.5)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8);">Official Roadmap</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ official.name }} |</span> {{ official.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">-</h4>
        </b-carousel-slide>
      </b-carousel>

    </b-container>


  </div>
</template>
<script>
export default {
  data() {
    return {
      roadmapsharesF: {},
      roadmapsharesS: {},
      postF: {},
      postS: {},
      officials: [],
      slide: 0,
      sliding: null,
      pictureList: [
        "https://picsum.photos/1024/480/?image=361",
        "https://picsum.photos/1024/480/?image=258",
        "https://picsum.photos/1024/480/?image=223",
        "https://picsum.photos/1024/480/?image=244",
        "https://picsum.photos/1024/480/?image=349",
      ],
    };
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    },
    getRoadmapshares() {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmapshare/main`)
        .then(res => {
          this.roadmapsharesF = res.data["roadmapshares"][0]
          this.roadmapsharesS = res.data["roadmapshares"][1]
          console.log(this.roadmapsharesF)
        })
        .catch(res => {
          console.log(res);
        });
    },
    getPostings() {
      axios
        .get(`${this.$store.getters.getBoardServer}/freeboard/main`)
        .then(res => {
          if (res.data["postings"][0]["content"].includes("[image]")) {
            this.postF = res.data["postings"][0]
            this.postF.content = "이미지가 포함된 게시글입니다."
            } else {
              this.postF = res.data["postings"][0]
            }
          if (res.data["postings"][1]["content"].includes("[image]")) {
            this.postS = res.data["postings"][1]
            this.postS.content = "이미지가 포함된 게시글입니다."
            } else {
              this.postS = res.data["postings"][1]
              console.log(postS)
            }
        })
        .catch(res => {
          console.log(res);
        });
    },
    getOfficial() {
      axios
      .get(`${this.$store.getters.getRoadmapServer}/roadmap/Official`)
      .then(res => {
        if (res.data.msg == "success") {
          if (res.data["roadmaps"].length) {
            this.officials = res.data["roadmaps"].slice(0, 2);
            // this.previewRoadmap(this.curriculumData[0].rmid);
            console.log(this.officials)
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
    goToShare() {
      this.$router.push({ name: "공유로드맵"})
      //, params()  << 오류라 일단 지웠어요
    },
    goToBoard() {

    },
    goToOfficial() {

    },
  },
  created() {
    this.getRoadmapshares();
    this.getPostings();
    this.getOfficial();
  }
};
</script>

<style scoped>
.mainContent {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>