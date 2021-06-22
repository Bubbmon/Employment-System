<template>
    <div id="app">
        <router-view class="main-pane"></router-view>
        <div class="footer"></div>
    </div>
</template>

<script>
import '@/assets/css/global.css'

export default {
    name: 'App',
    components: {

    },
    mounted() {
        window.addEventListener('unload', this.saveState)
    },
    methods: {
        saveState() {
            sessionStorage.setItem('state', JSON.stringify(this.$store.state))
        }
    }
}
</script>

<style>
#app {
    padding: 0;
    margin: 0;
    margin-top: 0;
}
.footer{
    height: 50px;

}
p {
    letter-spacing: 0.004em;
    font-weight: 400;
    font-size: 1.25rem;
    line-height: 1.6;
    color: #121212;
}
* {
    margin: 0;
    padding: 0;
    list-style: none;
    outline: none;
    box-sizing: border-box;
}
</style>
