<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>


    <b-container style="background: white; border-radius: 1rem;" class="py-4 mt-4">
      <b-row>
        <h1 class="ml-3 mb-0">{{ title }}</h1>
      </b-row>
      <hr class="my-2">
      <b-row>
        <b-col cols="1" class="pr-0 mb-2">? 무엇 img 들어갈 곳</b-col>
        <b-col>
          <h3>
            작성자 : {{ name }}
          </h3>
          <h5>
            {{ createDate }}
          </h5>
        </b-col>
      </b-row>
      <hr class="my-2">
      <b-row>
        <b-container>
          <b-card no-body class="border-0">
            <div class="inline-block" style="width: 100%;">
              <!-- goJS/start-->
              <Roadmap :roadmapMode=roadmapMode :roadmapData=roadmapData />
              <!--goJs/end -->
            </div>
          </b-card>
        </b-container>
        <!-- <p class="px-3">
          {{ content }}
        </p> -->
      </b-row>
      <b-row align-h="end" class="my-2" v-if="isWriter">
        <!-- 게시물의 uid와 현재 uid가 동일 할 때 보여줄 내용 -->
        <i class="far fa-trash-alt fa-lg mr-3" style="color: tomato" @click="deleteBoard"></i>
        <i class="far fa-edit fa-lg mr-3" style="color: Dodgerblue" @click="updateBoard"></i>
      </b-row>
      <b-row>
        <i class="far fa-thumbs-up fa-2x ml-3" v-if="!like" @click="clickLike"><span class="h3 ml-1">좋아요{{likeCnt}}</span></i>
        <i class="fas fa-thumbs-up fa-2x ml-3" v-if="like" @click="cancelLike"><span class="h3 ml-1">좋아요{{likeCnt}}</span></i>
        <button class="btn" style="background-color: rgb(256, 256, 256);" @click="goToCreate">내보내기</button>
      </b-row>
    </b-container>

  </div>
</template>

<script>
import Roadmap from '@/components/Roadmap/Roadmap.vue'
import "codemirror/lib/codemirror.css"; 
import "@toast-ui/editor/dist/toastui-editor.css"; 

export default {
  name: '',
  components: {
    Roadmap,
  },
  data() {
    return {
      roadmapMode: 0,
      roadmapData: {}, 
      name: '',
      title: '',
      createDate: '',
      uid: '',
      like: false,
      likeCnt: '',
      pid: '',
      isWriter: false,
      roadmap: '',
      rmid: '',
    }
  },
  created() {
    
  },
  mounted() {
    if (this.$route.params.roadmap) {
      this.getPostingInfo()
      this.previewRoadmap()
      this.likecheck()
    } else {
      this.$router.push({ name: '공유로드맵\'s' })
      return
    }
  },
  methods: {
    getPostingInfo() {
      this.roadmap = this.$route.params.roadmap
      this.name = this.$route.params.uname
      this.title = this.roadmap.title
      this.createDate = this.roadmap.createDate
      this.uid = this.roadmap.uid
      this.likeCnt = this.roadmap.likecnt
      this.pid = this.roadmap.pid
      this.rmid = this.roadmap.rmid
    },
    previewRoadmap() {
      axios.get(`${this.$store.getters.getServer}/roadmap/get/${this.rmid}`)
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
    likecheck(){
        axios.get(`${this.$store.getters.getServer}/roadmapshare/islike/${this.pid}`)
        .then((res) => {
          if(res.data.msg == 'success'){
            this.like = res.data.islike
          }else{
            alert("데이터 로드에 실패했습니다.");
          }
        }).catch((e) =>{
          console.log(e)
          alert("axios 오류")
        });
    },
    updateBoard() {
      // const pid = this.$route.query.pid
      // this.$router.push({name: 'update_board', query: { pid }})
    },
    deleteBoard() {
      // axios.delete(`${this.$store.getters.getServer}/freeboard/posting/${this.$route.query.pid}`)
      // .then(() => {
      //   alert('삭제가 정상적으로 완료되었습니다.')
      //   this.$router.push('/tmp_board')
      // })
    },
    clickLike() {
      axios.put(`${this.$store.getters.getServer}/roadmapshare/like/${this.$store.getters.getUid}/${this.pid}`)
      .then((res) => {
          if(res.data.msg == 'success'){
            
          }else{
            console.log(res)
            alert("데이터 로드에 실패했습니다.");
          }
      })
      .catch((e) =>{
        console.log(encodeURI);
      })
      this.like = true
      this.likeCnt ++
    },
    cancelLike() {
      axios.put(`${this.$store.getters.getServer}/roadmapshare/dislike/${this.$store.getters.getUid}/${this.pid}`)
      .then((res) => {
        console.log(res)
      }).catch((e) =>{
        console.log(e);

      })

      this.like = false
      this.likeCnt -- 
    },
    goToCreate() {
      this.$router.push({ name : '로드맵 수정하기', params: { rmid: this.rmid, CUMode: 0 }})
    },
  },
}
</script>

<style>

</style>