import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useTodoStore = defineStore('todo', () => {
  const id = ref('a')
  
  function changeId(newId) {
    id.value = newId;
  }

  return { id, changeId,  }
})
