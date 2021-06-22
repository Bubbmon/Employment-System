var state = sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')) : {
    token:null,
    id: null,
    identity:null
  }
  export default state
