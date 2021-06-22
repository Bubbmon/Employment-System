import Vue from 'vue'
import Vuex from 'vuex'
import state from './state'

Vue.use(Vuex)

export default new Vuex.Store({
  // state: {
  //   token:null,
  //   id:null
  //   //在全局中获取token
  //   //const { value: ret } = await this.$store.state.token;
  // },
  state,
  mutations: {
    //在全局中设置token
  //   this.$store.commit('getToken',{
  //     value: this.token
  // });
    getToken(state, value){
      state.token = value;
    },
    getId(state, value){
      state.id = value;
    },
    getIdentity(state, value){
      state.identity = value; //null:游客 0: 求职者 1:HR
    }
    
  },
  actions: {
  },
  modules: {
  }
})
