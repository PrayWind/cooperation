export default {
  namespaced: true,
  state: {
    id: 0,
    name: '',
    avatar: ''
  },
  mutations: {
    updateId(state, id) {
      state.id = id
    },
    updateName(state, name) {
      state.name = name
    },
    updateAvatar(state, avatar) {
      if (avatar == null || Object.keys(avatar).length == 0) {
        state.avatar = window.SITE_CONFIG.baseUrl + "avatar/default.png"
      } else {
        state.avatar = window.SITE_CONFIG.baseUrl + "avatar/" + avatar
      }
    }
  }
}
