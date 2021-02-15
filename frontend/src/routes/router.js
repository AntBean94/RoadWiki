import vue from 'vue';
import routes from './routes'; 
import VueRouter from 'vue-router';
import store from '@/store'

vue.use(VueRouter);

// configure routerthis.
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
  if (to.name === 'main') {
    if (store.getters.getAccessToken !== null)
    next({ name: '나의 로드맵' })
  }
  else if (to.name !== 'main' && to.name !== 'register' && to.name !== '게시판') { 
    if (store.getters.getAccessToken === null) { 
      next({name:'main'})
      alert('로그인이 필요한 서비스입니다.')
    }
  }
  next()
})