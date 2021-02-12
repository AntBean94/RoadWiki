<template>
  <div class='d-flex flex-column'>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>

    <div class="p-4 bg-secondary">
      <b-input 
        placeholder="제목" 
        class="form-control-alternative"
        v-model="title"
      />
    </div>

    <!--부트스트랩 드롭다운-->
    <div v-if="createMode">
      <b-dropdown id="dropdown-1" :text="selectedRoadmapName" class="m-md-2">
        <b-dropdown-item 
          @click="selectRoadmapToShare(item.rmid, item.name)"
          v-for="(item, index) in userRoadmapList" 
          :key="index">{{ item.name }} | {{ item.createDate }}
        </b-dropdown-item>
      </b-dropdown>
    </div>

    <div v-if="!createMode">
      <editor
        ref="toastuiEditor"
        :initialValue="initialText"
        :options="editorOptions"
        height="500px"
        initialEditType="wysiwyg"
        previewStyle="vertical"
        class="mx-4"
        v-model="editorText"
      />
    </div>

    <b-container class="mt-3">
      <b-row class="mx-2">
        <div
          class="ml-1 mt-2 mr-1 bg-warning rounded-pill py-1 px-2" 
          v-for="(tag, idx) in tags"
          :key="idx"
        >
          <span class="h5"># {{ tag }}</span>
          <span 
            class="ml-2 h5" 
            style="color:black"
            @click="delTag(idx)"
          >
            <i class="fas fa-times"></i>
          </span>
        </div>
      </b-row>
    </b-container>

    <div class="p-4 bg-secondary">
      <b-input 
        placeholder="태그를 추가해주세요" 
        class="form-control-alternative" 
        @keydown.enter="tagEnter"
        @blur="tagEnter"
        v-model="tagInput"
      />
    </div>

    <b-button
      v-if="!createMode"
      @click="datachange"
      class="mt-3 mx-4"
      variant="default"
    >
      저장
    </b-button>

    <b-button
      v-if="createMode"
      @click="createRoadmapToShare()"
      class="mt-3 mx-4"
      variant="default"
    >
      저장
    </b-button>

  </div>
</template>

<script>
import '@toast-ui/editor/dist/toastui-editor.css'
import 'codemirror/lib/codemirror.css'
import { Editor } from '@toast-ui/vue-editor'

export default {
  name: 'Editor',
  components: {
    'editor': Editor,
  },
  props: {
    // createMode[새글작성: 0, 로드맵 공유: 1]
    createMode: Number,
  },
  data() {
    return {
      initialText: '내용을 입력해주세요',
      editorOptions: {
        hideModeSwitch: false,
      },
      editorText: '',
      tags: ['첫번째 태그', '두번째 태그', '세번째 태그', '네번째 태그'],
      tagInput: '',
      title: '',
      userRoadmapList: [],
      roadmapData: "",
      rmid: "",
      selectedRoadmapName: "선택해 로드맵!",
    };
  },
  mounted() {
    // createMode: 1인 경우 userRoadmap 정보 호출
    if (this.createMode) {
      
      console.log('createMode는 ', this.createMode)
      const uid = String(this.$store.getters.getUid)
      axios.get(`${this.$store.getters.getServer}/roadmap/list/${uid}`)
        .then((res) => {
          if(res.data.msg == 'success') {
            // 유저의 roadmaplist
            if(res.data['roadmaps'].length){
              this.userRoadmapList = res.data['roadmaps'];
            }
          } else {
            alert("데이터 로드에 실패했습니다.")
          }
        }).catch((e) =>{
          console.log(e);
          alert("axios 오류")
        });
    }
  },
  methods: {
    datachange() {
      const content = this.$refs.toastuiEditor.invoke("getMarkdown");
      // console.log(typeof(content))
      axios
      .post(`${this.$store.getters.getServer}/freeboard/posting`, 
      { 
        'uid': this.$store.getters.getUid,
        'classifier': '자유게시판',
        'title': this.title, 
        'content': content
      })
      .then((res) => {
        this.$router.push('/tmp_board')
      })
      // this.editorText = content
      // this.$refs.toastuiViewer.editor.markdownValue = content
    },
    tagEnter() {
      console.log('엔터 찍힘')
      if (this.tagInput) {
        this.tags.push(this.tagInput)
        this.tagInput = ''
      }
    },
    delTag(idx) {
      this.tags.splice(idx, 1);
    },

    selectRoadmapToShare(rmid, name) {
      // 1. 로드맵 데이터 호출
      axios.get(`${this.$store.getters.getServer}/roadmap/get/${rmid}`)
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
      // 2. uid 호출
      const uid = this.$store.getters.getUid
      // 3. title 호출
      const title = this.title
      this.rmid = rmid

      // 4. item.name => this.에 보관
      this.selectedRoadmapName = name
    },
    createRoadmapToShare() {
      if (this.rmid && this.title) {
        axios.post(`${this.$store.getters.getServer}/roadmapshare/insert`, {
          rmid: this.rmid,
          uid: this.$store.getters.getUid,
          title : this.title,
        })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
        this.$router.push({ name: 'shareroadmap' })
      } else if (!this.title) {
        alert("글의 제목을 입력해주세요.")
      } else if (!this.rmid) {
        alert("공유할 로드맵을 선택해주세요.")
      }
    },
  },
  // watch: {
  //   editorText() {
  //     console.log(this.editorText)
  //   }
  // },
}
</script>

<style>

</style>