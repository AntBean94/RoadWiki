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
  if (to.name === 'MAINPAGE') {
    if (store.getters.getAccessToken !== null)
    next({ name: 'MAINPAGE' })
  }
  else if (to.name !== 'MAINPAGE' && to.name !== 'register' && to.name !== '게시판' && to.name !== '비밀번호찾기' && to.name !== '비밀번호재설정') { 
    if (store.getters.getAccessToken === null) { 
      next({ name: 'MAINPAGE' })
      // alert('로그인이 필요한 서비스입니다.')
    }
  }
  next()
})

