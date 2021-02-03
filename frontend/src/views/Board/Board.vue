<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>
    <light-table v-on:clickRow="clickOn" />

    <!-- <dark-table></dark-table> -->

    <board-sidebar
      v-on:goBack="clickOff"
      v-bind:selPid="selPid"
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
    clickOn: function(pid) {
      this.isSelected = true;
      this.selPid = pid;
      console.log(pid);
      console.log(this.isSelected);
    },
    clickOff: function() {
      this.isSelected = false;
      console.log(this.isSelected);
    }
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
