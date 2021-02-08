<template>
  <div>
    <base-header class="pb-5 pb-2 pt-2 pt-md-2 bg-gradient-default">
      <!-- Card stats -->
      <a
        :href="goToBack"
        class="btn"
        style="background-color: rgb(242, 214, 174);"
        >돌아가기</a
      >
      <button
        v-if="CUMode"
        class="btn"
        @click="updateRoadmap"
        style="background-color: rgba(256, 256, 256, 0.95);"
      >
        수정완료
      </button>

      <button
        v-else
        class="btn"
        @click="createRoadmap"
        style="background-color: rgb(181, 199, 211);"
      >
        생성완료
      </button>
      <!-- 사용법 modal / start -->
      <b-button
        v-b-modal.modal-1
        type="button"
        class="btn ml-4"
        title=""
        data-original-title="Copy to clipboard"
      >
        <div>
          <i class="ni ni-air-baloon"></i>
          <span>How to use</span>
        </div>
      </b-button>
    <b-form-input v-model="roadmapname" class="inline-block" placeholder="로드맵 제목을 입력해 주세요." style="width:30%; display:inline-block;"></b-form-input>
    <!-- 커리큘럼 히스토리 보여주기 -->
    <div>
      <!--부트스트랩 드롭다운-->
      <div>
        <b-dropdown id="dropdown-1" text="이전 수정 기록" class="m-md-2">
          <b-dropdown-item 
            @click="previewRoadmap(item.rmid)" 
            v-for="(item, index) in logData" 
            :key="index">{{ item.createDate }} | {{ item.name }}
          </b-dropdown-item>
        </b-dropdown>
      </div>
    </div>
    <!-- 커리큘럼 히스토리 끝 -->

      <b-modal id="modal-1" title="BootstrapVue">
        <h3>로드위키 사용법</h3>
        <h4>❤ Read</h4>
        <li>
          '내 로드맵 보기'에서 나만의 로드맵을 볼 수 있습니다.
        </li>
        <li>
          상위의 리스트에서 파일을 클릭하시면 원하시는 로드맵을 볼 수 있습니다.
        </li>
        <li>
          수정버튼을 누르시면 로드맵을 수정 할 수 있는 페이지로 넘어갑니다.
        </li>
        <h4>❤ Create</h4>
        <li>
          원하시는 커리큘럼을 선택하세요.
        </li>
        <li>
          원하시는 커리큘럼에서 내보내기 버튼을 누르시면 내 로드맵으로
          불러오기가 가능합니다.
        </li>
        <li>
          서비스에서 제공하는 로드맵에서 나만의 로드맵으로 맞춤 설정이
          가능합니다!
        </li>
        <h4>❤ Update</h4>
        <li>
          수정하고 싶은 요소를 클릭해 delete버튼을 누르시면 요소가 삭제됩니다.
        </li>
        <li>
          오른쪽에는 커리큘럼의 정보가 제공됩니다.
        </li>
        <li>
          왼쪽에는 서비스에서 추천해주는 로드맵 요소들을 끌어다 내 로드맵에 옮길
          수 있습니다.
        </li>
        <li>
          선 이수체계에 맞도록 요소의 상, 하, 좌, 우에서 가지를 요소에 연결해
          보세요.
        </li>
        <h4>❤ Delete</h4>
        <li>
          로드맵이 마음에 들지 않으시다면 삭제도 가능합니다.
        </li>
        <li>
          삭제버튼을 눌러 로드맵을 삭제하세요.
        </li>
      </b-modal>
      <!-- 사용법 modal / end -->
    </base-header>

    <b-container fluid class="mt-1">
      <b-row>
        <b-col>
          <b-card no-body class="border-0">
            <div style="width: 100%;">
              <Roadmap :roadmapMode=roadmapMode :roadmapData=roadmapData @create-roadmap=createRoadmap ref="roadmap"/>
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import dropdown from 'vue-dropdowns';
import Roadmap from '@/components/Roadmap'     

export default {
  name: "",
  components: {
    dropdown: dropdown,
    Roadmap,
  },
  props: {
    rmid: {
      type: Number
    },
    rmorder: {
      type: Number
    },
    CUMode: {
      type: Number
    },
  },
  data() {
    return {
      goToBack: "#/read-user-roadmap",
      roadmapData: {},
      roadmapname: "",
      logData: [],
      roadmapMode: 1,
    };
  },
  created(){
  },
  mounted() {
    this.readRoadmap();
    // 수정로그 가져오기
    this.readRoadmapLog();
  },
  watch: {
  },
  computed: {
  },
  methods: {
    // read 요청보내기
    readRoadmap() {
      if(this.rmid == 0){
        this.roadmapData = { "class": "go.GraphLinksModel",
        "linkFromPortIdProperty": "fromPort",
        "linkToPortIdProperty": "toPort",
        "nodeDataArray": [
      ],
        "linkDataArray": [
      ]}
      }else{
      axios.get(`${this.$store.getters.getServer}/roadmap/get/${this.rmid}`)
        .then((res) => {
          if(res.data.msg == 'success'){
          this.roadmapData = JSON.parse(res.data['roadmaps'].tmp);
          this.roadmapname = res.data['roadmaps'].name;
          }else{
            alert("데이터 로드에 실패했습니다.")
          }
        }).catch((e) =>{
          alert("axios 오류", "readRoadmap")
        });
      }
    },

    // 로드맵 로그 가져오는 함수(mounted에서 rmorder를 불러온뒤 실행)
    readRoadmapLog(){
      if(this.CUMode == 1){
         axios.get(`${this.$store.getters.getServer}/roadmap/log/${this.$store.getters.getUid}/${this.rmorder}`)
        .then((res) => {
          if(res.data.msg == 'success'){
            this.logData = res.data['roadmaps'];  
          }else{
            console.log(e);
            alert("데이터 로드에 실패했습니다.")
          }
        }).catch((e) =>{
          alert("axios 오류")
        });
      }
    },

    // update 요청보내기
    updateRoadmap() {
      const childRoadmapData = this.$refs.roadmap.serveRoadmap()
      axios
        .post(`${this.$store.getters.getServer}/roadmap/update`, {
          uid: this.$store.getters.getUid,
          rmorder: this.rmorder,
          name: this.roadmapname,
          tmp: childRoadmapData
        }
      )
      .then((res) => {
        if(res.data.msg == 'success'){
        this.$router.push({ name: 'read_user_roadmap' })
          }else{
            alert("업데이트 실패했습니다.")
          }
        })
        .catch(e => {
          alert("axios 오류");
        });
    },
    createRoadmap() {
      const childRoadmapData = this.$refs.roadmap.serveRoadmap()
      axios
        .post(`${this.$store.getters.getServer}/roadmap/create`, {
          uid: this.$store.getters.getUid,
          name: this.roadmapname,
          tmp: childRoadmapData
        }
      )
      .then((res) => {
        if(res.data.msg == 'success'){
          this.$router.push({ name: 'read_user_roadmap' })
        }else{
          alert("생성에 실패했습니다.")
        }
        }).catch((e) =>{
          alert('axios 오류')
        });
    },
    checkCur(e) {
      // 차후에 DB에 요청을 보낸다음 DB정보로 반영
      this.headertext = curriculumName;
    },

    previewRoadmap(clickrmid) {
        axios.get(`${this.$store.getters.getServer}/roadmap/get/${clickrmid}`)
        .then((res) => {
          if(res.data.msg == 'success'){
          this.roadmapData = JSON.parse(res.data['roadmaps'].tmp);
          }else{
            alert("데이터 로드에 실패했습니다.")
          }
        })
        .catch(e => {
          console.log(e);
          alert("axios 오류");
        });
    },
  }
};
</script>

<style></style>
