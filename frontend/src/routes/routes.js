import DashboardLayout from '@/views/Layout/DashboardLayout.vue';
import AuthLayout from '@/views/Pages/AuthLayout.vue';
import NothingMain from '@/views/NothingMain.vue';

import NotFound from '@/views/NotFoundPage.vue';

const routes = [
  {
    path: '/main',
    name: 'main',
    component: NothingMain,
  },
  {
    path: '/',
    redirect: 'main',
    component: DashboardLayout,
    children: [
      {
        path: '/dashboard',
        redirect: '/read-user-roadmap',
        name: 'dashboard',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "demo" */ '../views/Dashboard.vue')
      },
      {
        path: '/icons',
        name: 'icons',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Icons.vue')
      },
      {
        path: '/profile',
        name: 'profile',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/UserProfile.vue')
      },
      {
        path: '/profile-update',
        name: 'profile-update',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/ProfileUpdate.vue')
      },
              
      {
        path: '/maps',
        name: 'maps',
        component: () => import(/* webpackChunkName: "demo" */ '../views/GoogleMaps.vue')
      },
      {
        path: '/detail-contents',
        name: 'Detail Content',
        component: () => import(/* webpackChunkName: "demo" */ '../components/Board/DetailContent.vue')
      },
      {
        path: '/tmp_board',
        name: 'tmp_board',
        component: () => import('../views/Board/Board.vue')
      },
      {
        path: '/create-board',
        name: 'create_board',
        component: () => import('../views/Board/CreateBoard.vue'),
        props: true
      },
      {
        path: '/detail-board',
        name: 'detail_board',
        props: true,
        component: () => import('../views/Board/DetailBoard.vue')
      },
      {
        path: '/update-board',
        name: 'update_board',
        props: true,
        component: () => import('../views/Board/UpdateBoard.vue')
      },
      {
        path: '/read-user-roadmap',
        name: 'read_user_roadmap',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Roadmap/ReadUserRoadmap.vue')
      },
      {
        path: '/update-user-roadmap',
        name: 'update_user_roamdap',
        component: () => import('../views/Roadmap/UpdateUserRoadmap.vue'),
        props: true
      },
      {
        path: '/official-roadmap',
        name: 'official_roadmap',
        component: () => import('../views/Roadmap/OfficialRoadmap.vue'),
        props: true
      },
      {
        path: '/admin',
        name: 'admin',
        component: () => import('../views/Roadmap/AdminRoadmap.vue'),
      },
      {
        path: '/calendar',
        name: 'calendar',
        component: () => import('../views/Calendar/Calendar.vue'),
        props: true
      },
      {
        path: '/shareroadmap',
        name: 'shareroadmap',
        component: () => import('../views/ShareRoadmap/ShareBoard.vue'),
      },
    ]
  },
  {
    path: '/',
    // redirect: 'login',
    component: AuthLayout,
    children: [
      {
        path: '/login',
        name: 'login',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Login.vue')
      },
      {
        path: '/register',
        name: 'register',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Register.vue')
      },
      { path: '*', component: NotFound },
    ]
  },
];

export default routes;