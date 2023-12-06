import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useReplyStore = defineStore('reply', () => {
  const originNo = ref(0)//state

  return { originNo}
})
