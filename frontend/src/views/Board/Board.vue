<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>
    <b-container>
      <b-row align-h="end">
        <!-- <b-button variant="primary" class="mt-2" @click="detailBoard">detail board</b-button> -->
        <b-button variant="primary" class="mt-2" @click="createBoard">새 글 작성</b-button>
      </b-row>
    </b-container>

    <light-table @clickRow="clickOn" />

    <!-- <dark-table></dark-table> -->

    <board-sidebar
      @goBack="clickOff"
      :selPid="selPid"
      v-if="isSelected"
    />
  </div>
</template>
<script>
import {
  Dropdown,
  DropdownItem,
  DropdownMenu,
  Table,
  TableColumn
} from "element-ui";
import LightTable from "./Tables/LightTable";
import DarkTable from "./Tables/DarkTable";
import Detail from "./Posting/Detail";
import BoardSidebar from "./BoardSidebar";

export default {
  components: {
    LightTable,
    DarkTable,
    Detail,
    BoardSidebar,
    [Dropdown.name]: Dropdown,
    [DropdownItem.name]: DropdownItem,
    [DropdownMenu.name]: DropdownMenu,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data() {
    return {
      isSelected: false,
      tWidth: "550px",
      selPid: ""
    };
  },
  methods: {
    clickOn(pid) {
      this.isSelected = true;
      this.selPid = pid;
      console.log(pid);
      console.log(this.isSelected);
    },
    clickOff() {
      this.isSelected = false;
      console.log(this.isSelected);
    },
    createBoard() {
      this.$router.push({name: 'create_board' })
    },
    // detailBoard() {
    //   this.$router.push({name: 'detail_board', query: { pid: pid }})
    // },
  },
  created() {
    console.log(this.$store.getters.getUid);
  }
};
</script>
<style>
.el-table.table-dark {
  background-color: #172b4d;
  color: #f8f9fe;
}

.el-table.table-dark th,
.el-table.table-dark tr {
  background-color: #172b4d;
}

.el-table.table-dark td,
.el-table.table-dark th.is-leaf {
  border-bottom: none;
}

body {
  -ms-overflow-style: none;
}
::-webkit-scrollbar {
  display: none;
}
.box {
  -ms-overflow-style: none;
}
.box::-webkit-scrollbar {
  display: none;
}
</style>
