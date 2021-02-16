import vue from 'vue';
import routes from './routes'; 
import VueRouter from 'vue-router';
import store from '@/store'

vue.use(VueRouter);

//intro페이지를 위한 스크롤 이벤트 처리 
const router = new VueRouter({
  routes, // short for routes: routes
  linkActiveClass: 'active',
  mode: 'history',
  
  scrollBehavior: (to, from ,savedPosition) => {
    if (savedPosition) {
      return savedPosition;
    }
    if (to.hash) {
      return { selector: to.hash };
    }
    return { x: 0, y: 0 };
  }
});

export default router;
  
router.beforeEach((to, from, next) => {
  if (store.getters.getAccessToken === null)
    if (sessionStorage.getItem('auth-token') !== null)
      store.commit('LOADUSERTOKEN');
  if (to.name === 'intro') {
    if (store.getters.getAccessToken !== null)
    next({ name: 'read_user_roadmap' })
  }
  else if (to.name !== 'intro' && to.name !== 'register' && to.name !== 'board') { 
    if (store.getters.getAccessToken === null) { 
      next({name:'intro'})
      alert('로그인이 필요한 서비스입니다.')
    }
  }
  next()
})