<script setup>
import { useTodoStore } from "@/stores/todo";
import { storeToRefs } from "pinia";
import {ref} from "vue";

const todoStore = useTodoStore();
const {id }=storeToRefs(todoStore);
//todoStore.changeId("b")

const newId=ref('')
</script>

<template>
  <div>
    ID : {{ id }}
    <input type="text" v-model="newId">
    <button @click="todoStore.changeId(newId)">수정</button>
  </div>
  
</template>

<style scope>

</style>