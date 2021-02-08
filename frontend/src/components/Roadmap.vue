<template>
  <!--goJS/start-->
  <div style="width: 100%; display: flex; justify-content: space-between; z-index:1;">
    <div
      v-show="roadmapMode"
      ref="myPaletteDiv"
      style="width: 150px; margin-right: 2px; background-color: #F9F8F3;"
    ></div>
    <div ref="myDiagramDiv" style="flex-grow: 1; height: 750px; background-color: #F9F8F3;" @click="checkCur">
    </div>
      <!-- 커리큘럼 데이터 출력 카드/start -->
    <b-card
      title="Curriculum Information"
      style="width: 252px;"
    >
    <hr>
      <h3>{{ headertext }}</h3>
      <hr>
      <b-card-text>
        <base-input label="시작날짜-종료날짜" @click="checkDate">
        <flat-pickr 
          slot-scope="{focus, blur}"
          @on-open="focus"
          @on-close="blur"
          :config="{allowInput: true, mode: 'range',}"
          class="form-control datepickr"
          v-model="dates.range"
        >
        </flat-pickr>
        </base-input>
      </b-card-text>
      <hr>
      <span>커리큘럼 설명</span>
      <hr>
      <b-card-text>
        <b-form-input 
          v-model="memotext" 
          placeholder="Enter your memo" 
        ></b-form-input>
      </b-card-text>
    </b-card>
    <!-- 커리큘럼 데이터 출력 카드/end -->
  </div>
  <!--goJs/end-->
</template>

<script>
// flatPickr - Hindi: 날짜 설정 부속기능
import {Hindi} from 'flatpickr/dist/l10n/hi.js';
import dropdown from 'vue-dropdowns';

// src\views\Roadmap\RoadMap.vue
// Roadmap 폴더 명 변경을 위한 주석
// 코드 변환 시작
let go = window.go;
let $ = go.GraphObject.make;
let myDiagram;
let myPalette;
// node 속성(card에 띄우기위한) 체크위한 전역변수(여기서만 사용)
let curriculumData = -1;

// 커리큘럼 클릭시 요청을통해 받아온 데이터를 여기에 저장하면 됨
let recommendCurData = [
  // 실제 프로젝트 default data 최초 클릭할 정보가 필요
]   

export default {
  name: 'Roadmap',
  props: {
    roadmapMode: Number,
    roadmapData: Object,
  },
  data() {
    return {  
      ismounted: false,
      headertext: '',
      dates :{
        range : '2020-01-01 to 2020-01-01'
      },
      memotext : '',
      // Get more form https://flatpickr.js.org/options/
      config: {
        wrap: true, // set wrap to true only when using 'input-group'
        altFormat: 'M j, Y',
        altInput: true,
        dateFormat: 'Y-m-d',
        locale: Hindi, // locale for this instance only          
      },
    }
  },
  components: {
    dropdown: dropdown
  },
  created() {

  },
  mounted() {
    myDiagram = $(go.Diagram, this.$refs.myDiagramDiv, {
      initialContentAlignment: go.Spot.Center,
      "undoManager.isEnabled": true  
    });

    // 페이지에 변화가 있을 때 title 및 save 버튼 활성화
    // when the document is modified, add a "*" to the title and enable the "Save" button
    // jquery사용되어 있어서 우리 수정완료버튼을 활성하시키고 싶으면 방식을 바꿔야함
    myDiagram.addDiagramListener("Modified", function(e) {
      // var button = document.getElementById("SaveButton");
      // console.log('?',button) => 아무것도 안찍힘
      // if (button) button.disabled = !myDiagram.isModified;
      // var idx = document.title.indexOf("*"); 
      // console.log('?',document.title) => 페이지 전체 제목이 출력 index.html 제목(roadwiki)
      // if (myDiagram.isModified) {
      //   if (idx < 0) document.title += "*";
      // } else {
      //   if (idx >= 0) document.title = document.title.substr(0, idx);
      // }
    });

    // GUI 시작
    // 대분류 커리큘럼 모델
    myDiagram.nodeTemplateMap.add("blue", // the default category
      $(go.Node, "Table", this.nodeStyle(),
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(go.Panel, "Auto",
          $(go.Shape, "RoundedRectangle",
            {
              fill: "rgb(255, 255 ,255)",
              stroke: "rgb(15, 76, 129)",
              strokeWidth: 3,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 8,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: false
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

    // 중분류 커리큘럼 모델
    myDiagram.nodeTemplateMap.add("black", // the default category
      $(go.Node, "Table", this.nodeStyle(),
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(go.Panel, "Auto",
          $(go.Shape, "RoundedRectangle",
            {
              fill: "rgb(255, 255, 255)",
              stroke: "rgb(132, 137, 140)",
              strokeWidth: 2.5,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 8,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: false
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

    // 소분류 커리큘럼 모델
    myDiagram.nodeTemplateMap.add("green", // the default category
      $(go.Node, "Table", this.nodeStyle(),
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(go.Panel, "Auto",
          $(go.Shape, "RoundedRectangle",
            {
              fill: "rgb(255, 255, 255)",
              stroke: "#307363",
              strokeWidth: 2,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 8,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: false
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

    // 시작 모델
    myDiagram.nodeTemplateMap.add("Start",
      $(go.Node, "Table", this.nodeStyle(),
        $(go.Panel, "Spot",
          $(go.Shape, "Circle", {
            desiredSize: new go.Size(70, 70),
            fill: "#ffffff",
            stroke: "#F04A5E",
            strokeWidth: 3.5
          }),
          $(go.TextBlock, "Start", this.textStyle(), new go.Binding("text"))
        ),
        // three named ports, one on each side except the top, all output only:
        this.makePort("L", go.Spot.Left, go.Spot.Left, true, false),
        this.makePort("R", go.Spot.Right, go.Spot.Right, true, false),
        this.makePort("B", go.Spot.Bottom, go.Spot.Bottom, true, false)
      )
    );

    // 종료 모델
    myDiagram.nodeTemplateMap.add("End",
      $(go.Node, "Table", this.nodeStyle(),
        $(go.Panel, "Spot",
          $(go.Shape, "Circle", {
            desiredSize: new go.Size(60, 60),
            fill: "#ffffff",
            stroke: "#8D2040",
            strokeWidth: 3.5
          }),
          $(go.TextBlock, "End", this.textStyle(), new go.Binding("text"))
        ),
        // three named ports, one on each side except the bottom, all input only:
        this.makePort("T", go.Spot.Top, go.Spot.Top, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.Left, false, true),
        this.makePort("R", go.Spot.Right, go.Spot.Right, false, true)
      )
    );

    // taken from ../extensions/Figures.js:
    go.Shape.defineFigureGenerator("File", function(shape, w, h) {
      var geo = new go.Geometry();
      var fig = new go.PathFigure(0, 0, true); // starting point
      geo.add(fig);
      fig.add(new go.PathSegment(go.PathSegment.Line, 0.75 * w, 0));
      fig.add(new go.PathSegment(go.PathSegment.Line, w, 0.25 * h));
      fig.add(new go.PathSegment(go.PathSegment.Line, w, h));
      fig.add(new go.PathSegment(go.PathSegment.Line, 0, h).close());
      var fig2 = new go.PathFigure(0.75 * w, 0, false);
      geo.add(fig2);
      // The Fold
      fig2.add(new go.PathSegment(go.PathSegment.Line, 0.75 * w, 0.25 * h));
      fig2.add(new go.PathSegment(go.PathSegment.Line, w, 0.25 * h));
      geo.spot1 = new go.Spot(0, 0.25);
      geo.spot2 = go.Spot.BottomRight;
      return geo;
    });

    // replace the default Link template in the linkTemplateMap
    // 링크 결합, 설정관련 코드
    myDiagram.linkTemplate = $(
      go.Link, // the whole link panel
      {
        routing: go.Link.AvoidsNodes,
        curve: go.Link.JumpOver,
        corner: 5,
        toShortLength: 4,
        relinkableFrom: true,
        relinkableTo: true,
        reshapable: true,
        resegmentable: true,

        // mode에 따라 바뀌어야 하는 부분--------------------------------------------------------|
        // 마우스 오버시 이펙트 효과 부여
        mouseEnter: function(e, link) {
          link.findObject("HIGHLIGHT").stroke = "rgba(255, 255, 255, 1)";
        },
        mouseLeave: function(e, link) {
          link.findObject("HIGHLIGHT").stroke = "transparent";
        },
        //----------------------------------------------------------------------------------------|
        selectionAdorned: false
      },
      new go.Binding("points").makeTwoWay(),
      $(
        go.Shape, // 화살표 클릭시 하이라이트 효과 설정
        {
          isPanelMain: true,
          strokeWidth: 5,
          stroke: "transparent",
          name: "HIGHLIGHT"
        }
      ),
      $(
        go.Shape, // 화살표 바디 모양
        { isPanelMain: true, stroke: "#1B443C", strokeWidth: 2.5 },
        new go.Binding("stroke", "isSelected", function(sel) {
          return sel ? "#1B443C" : "#1B443C";
        }).ofObject()
      ),
      $(
        go.Shape, // 화살표 모양
        {
          toArrow: "Triangle",
          strokeWidth: 1.5,
          stroke: "#1B443C",
          fill: "#307362"
        }
      ),
      $(
        go.Panel,
        "Auto", // 링크 라벨 (안보이게 설정해둠)
        {
          visible: false,
          name: "LABEL",
          segmentIndex: 2,
          segmentFraction: 0.5
        },
        new go.Binding("visible", "visible").makeTwoWay(),
        $(
          go.Shape,
          "RoundedRectangle", // the label shape
          { fill: "#F8F8F8", strokeWidth: 0 }
        ),
        $(
          go.TextBlock,
          "Yes", // the label
          {
            textAlign: "center",
            font: "10pt helvetica, arial, sans-serif",
            stroke: "#333333",
            editable: false
          },
          new go.Binding("text").makeTwoWay()
        )
      )
    );

    // 어떤 커리큘럼을 눌렀는지 체크 => 커리큘럼 추천에 활용할 데이터 추출
    // 추천 로직 1 단계
    myDiagram.addDiagramListener("ObjectSingleClicked", function(e) {
      curriculumData = e.subject.part.data;
      
    });

    myDiagram.addDiagramListener("BackgroundSingleClicked", function(e) {
      curriculumData = -1
    });

    myDiagram.addDiagramListener("ObjectDoubleClicked", function(e){
    });
    

    // 팔레트 설정 관련 코드
    // 노드모델에 변경사항이 있다면 반드시 여기서 확인 해주셔야 합니다.
    myPalette = 
      $(go.Palette, this.$refs.myPaletteDiv, // must name or refer to the DIV HTML element
        {
          // Instead of the default animation, use a custom fade-down
          "animationManager.initialAnimationStyle": go.AnimationManager.None,
          InitialAnimationStarting: this.animateFadeDown, // Instead, animate with this function
          nodeTemplateMap: myDiagram.nodeTemplateMap, // share the templates used by myDiagram
          //######################################################### 추천 커리 백엔드 연동부 핵심코드!
          // 추천 컴포넌트를 띄우려면 여기에 데이터를 가져와서 랜더링
          model: new go.GraphLinksModel(
            // 추천 커리큘럼 전역변수로 저장되어있음
            recommendCurData
          )
        }
      );

    // 링크연결시 화살표가 직교하는 모양으로 보일 수 있도록 하는 설정
    myDiagram.toolManager.linkingTool.temporaryLink.routing = go.Link.Orthogonal;
    myDiagram.toolManager.relinkingTool.temporaryLink.routing = go.Link.Orthogonal;

    // mode에 따라 분류 -----------------------------------------------------------------|
    // 수정 없이 읽기 
    if (this.roadmapMode) {
      myDiagram.isReadOnly = false;
    } else {
      myDiagram.isReadOnly = true;
    }
    //-----------------------------------------------------------------------------------|

    // 필요없음
    this.readRoadmap();
    // // 수정로그 가져오기
    // update쪽으로 옮기기
    // this.readRoadmapLog();
    this.getRecommendCur();
    // 팔레트 초기화
    curriculumData = -1;
  },
  watch: {
    // head 데이터 변경때 마다 실행(즉, 커리큘럼 클릭시 실행)
    headertext: function () {
      // 데이터 호출하는 함수
      this.getRecommendCur()
    },
    roadmapData: function (e) {
      myDiagram.model = go.Model.fromJson(e);    
    }
  },
  computed: {
  },
  methods: {
    // 다이어그램 모델관련 함수
    nodeStyle() {
      return [
        // The Node.location comes from the "loc" property of the node data,
        // converted by the Point.parse static method.
        // If the Node.location is changed, it updates the "loc" property of the node data,
        // converting back using the Point.stringify static method.
        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
        {
          // the Node.location is at the center of each node
          locationSpot: go.Spot.Center
        }
      ];
    },
    makePort(name, align, spot, output, input) {
      var horizontal = align.equals(go.Spot.Top) || align.equals(go.Spot.Bottom);
      // the port is basically just a transparent rectangle that stretches along the side of the node,
      // and becomes colored when the mouse passes over it
      return $(go.Shape,
        {
          fill: "transparent",  // changed to a color in the mouseEnter event handler
          strokeWidth: 0,  // no stroke
          width: horizontal ? NaN : 8,  // if not stretching horizontally, just 8 wide
          height: !horizontal ? NaN : 8,  // if not stretching vertically, just 8 tall
          alignment: align,  // align the port on the main Shape
          stretch: (horizontal ? go.GraphObject.Horizontal : go.GraphObject.Vertical),
          portId: name,  // declare this object to be a "port"
          fromSpot: spot,  // declare where links may connect at this port
          fromLinkable: output,  // declare whether the user may draw links from here
          toSpot: spot,  // declare where links may connect at this port
          toLinkable: input,  // declare whether the user may draw links to here
          cursor: "pointer",
      })
    },
    // 글씨체, 스타일 수정 필요(프론트 집중기간)
    textStyle() {
      return {
        font: "bold 11pt Lato, Helvetica, Arial, sans-serif",
        stroke: "#000000"
      }
    },
    // 링크라벨 함수(필요없으면 삭제가능)
    showLinkLabel(e) {
      var label = e.subject.findObject("LABEL");
      if (label !== null)
        label.visible = e.subject.fromNode.data.category === "Conditional";
    },
    // 로드될때 위에서 아래로 올라오는 애니메이션 
    animateFadeDown(e) {
      var diagram = e.diagram; 
      var animation = new go.Animation();
      animation.isViewportUnconstrained = true; // So Diagram positioning rules let the animation start off-screen
      animation.easing = go.Animation.EaseOutExpo;
      animation.duration = 900;
      // Fade "down", in other words, fade in from above
      animation.add(diagram, 'position', diagram.position.copy().offset(0, 200), diagram.position);
      animation.add(diagram, 'opacity', 0, 1);
      animation.start();
    },
    checkCur(e) {
      // 차후에 DB에 요청을 보낸다음 DB정보로 반영
      this.headertext = curriculumData.text;
    },
    getRecommendCur() {
      let color
      let url
      if(curriculumData == -1){
        url = `${this.$store.getters.getServer}/curriculum/suggest`;
        color = "blue";
      }
      else if(curriculumData.mdid != 0) {
        url = `${this.$store.getters.getServer}/curriculum/suggest/${curriculumData.bdid}/${curriculumData.mdid}`;
        color = "green";
      }else if(curriculumData.bdid != 0){
        url = `${this.$store.getters.getServer}/curriculum/suggest/${curriculumData.bdid}`;
        color = "black";

      }
        axios.get(url)
        .then((res) => {
          
          for( var i = 0 ; i < res.data['suggest'].length ; i++){
            res.data['suggest'][i].category = color;
            res.data['suggest'][i].startdate = "";
            res.data['suggest'][i].enddate = "";
            res.data['suggest'][i].memo = "";
            }
          recommendCurData = res.data['suggest']       
          myPalette.model.nodeDataArray = recommendCurData   
        })
        .catch((e) => {
          console.error(e)
        })

    },
    readRoadmap() {
      myDiagram.model = go.Model.fromJson(this.roadmapData);
    },
    serveRoadmap() {
      return myDiagram.model.toJson();
    },
    checkDate() {
    }
  },
}
</script>

<style>

</style>