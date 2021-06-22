import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
// 避免push的时候avoid redundant navigation的报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

let router = new VueRouter({
    routes: [
        {
            path: "/",
            component: () => import(/*webpackChunkName:"SearchT"*/'@/views/SearchT'),
            meta: { title: "search" }
        },
        {
            path: "/search",
            component: () => import(/*webpackChunkName:"SearchT"*/'@/views/SearchT'),
            meta: { title: "search" }
        },
        {
            path: "/retrievalT",
            component: () => import(/*webpackChunkName:"Retrieval"*/'@/views/RetrievalT'),
            meta: { title: "检索结果" }
        },
        {
            path: "/detailT",
            component: () => import(/*webpackChunkName:"Detail"*/'@/views/DetailT'),
            meta: { title: "详情页" }
        },
        {
            path: "/infoDetailT",
            component: () => import(/*webpackChunkName:"infoDetail"*/'@/views/InfoDetailT'),
            meta: { title: "资讯" }
        },
        {
            path: "/companyT",
            component: () => import(/*webpackChunkName:"company"*/'@/views/CompanyT'),
            meta: { title: "公司简介" }
        },
        {
            path: "/login",
            component: () => import(/*webpackChunkName:"Detail"*/'@/views/Login'),
            meta: { title: "登录页" }
        },
        {
            path: "/signUp",
            component: () => import(/*webpackChunkName:"Detail"*/'@/views/SignUp'),
            meta: { title: "注册页" }
        },
        {
            path: "/searchC",
            component: () => import(/*webpackChunkName:"SearchT"*/'@/views/Consumer/SearchC'),
            meta: { title: "search" }
        },
        {
            path:"/infoDetailC",
            component:()=>import(/*webpackChunkName:"InfoDetailC"*/'@/views/Consumer/InfoDetailC'),
            meta:{title:"infoDetail"}
        },
        {
            path: "/retrievalC",
            component: () => import(/*webpackChunkName:"Retrieval"*/'@/views/Consumer/RetrievalC'),
            meta: { title: "检索结果" }
        },
        {
            path: "/detailC",
            component: () => import(/*webpackChunkName:"Detail"*/'@/views/Consumer/DetailC'),
            meta: { title: "详情页" }
        },
        {
            path: "/companyC",
            component: () => import(/*webpackChunkName:"company"*/'@/views/Consumer/CompanyC'),
            meta: { title: "公司简介" }
        },
        {
            path: "/selfCenterC",
            component: () => import(/*webpackChunkName:"Detail"*/'@/views/Consumer/SelfCenterC'),
            meta: { title: "个人中心" }
        },
        {
            path: "/selfResume",
            component: () => import(/*webpackChunkName:"Resume"*/'@/views/Consumer/SelfResume'),
            meta: { title: "我的简历" }
        },
        {
            path: "/myChatC",
            component: () => import(/*webpackChunkName:"chat"*/'@/views/Consumer/MyChatC'),
            meta: { title: "我的聊天" }
        },
        {
            path: "/chatListC",
            component: () => import(/*webpackChunkName:"chat"*/'@/views/Consumer/ChatListC'),
            meta: { title: "我的聊天" }
        },
        {
            path: "/searchHR",
            component: () => import(/*webpackChunkName:"SearchT"*/'@/views/HR/SearchHR'),
            meta: { title: "search" }
        },
        {
            path: "/retrievalHR",
            component: () => import(/*webpackChunkName:"Retrieval"*/'@/views/HR/RetrievalHR'),
            meta: { title: "检索结果" }
        },
        {
            path: "/detailHR",
            component: () => import(/*webpackChunkName:"Detail"*/'@/views/HR/DetailHR'),
            meta: { title: "详情页" }
        },
        {
            path: "/companyHR",
            component: () => import(/*webpackChunkName:"company"*/'@/views/HR/CompanyHR'),
            meta: { title: "公司简介" }
        },
        {
            path: "/selfCenterHR",
            component: () => import(/*webpackChunkName:"Detail"*/'@/views/HR/SelfCenterHR'),
            meta: { title: "个人中心" }
        },
        {
            path: "/selfCenterHR",
            component: () => import(/*webpackChunkName:"Detail"*/'@/views/HR/SelfCenterHR'),
            meta: { title: "个人中心" }
        },
        {
            path:"/issueRecruimentInfo",
            component: () => import(/*webpackChunkName:"issue"*/'@/views/HR/IssueRecruimentInfo'),
            meta:{title:"发布招聘信息"}
        },
        {
            path:"/recruimentList",
            component:()=>import(/*webpackChunkName:"resumeList"*/'@/views/HR/RecruimentList'),
            meta:{title:"已发布的招聘信息"}
        },
        {
            path: "/chatListHR",
            component: () => import(/*webpackChunkName:"chat"*/'@/views/HR/ChatListHR'),
            meta: { title: "我的聊天" }
        },
        {
            path:"/resumeList",
            component:()=>import(/*webpackChunkName:"resumeList"*/'@/views/HR/ResumeList'),
            meta:{title:"简历列表"}
        },
        {
            path:"/myChatHR",
            component:()=>import(/*webpackChunkName:"myChat"*/'@/views/HR/MyChatHR'),
            meta:{title:"我的聊天"}
        }
       
    ]
})

export default router