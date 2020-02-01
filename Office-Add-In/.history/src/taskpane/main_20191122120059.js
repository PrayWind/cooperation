import addIn from "../api/addIn"

var vm = new Vue({
    el: '#app',
    data: function(){
        return {
            msg: '测试Vue',
            visible: false
        }
    },
    methods: {
        click1: function() {
            
            // axios
            //     .get('https://www.runoob.com/try/ajax/json_demo.json')
            //     .then(response => (this.msg = response))
            //     .catch(function (error) { // 请求失败处理
            // console.log(error);
            // });

            // axios({
            //     method: 'post',
            //     url: '/login',
            //     data: {
            //         username: "uddatays@uddata.partner.onmschina.cn",
            //         password: "Abc123.."
            //     }
            // }).then(function (response) {
            //     // 使用JSON.stringify(response)可以看到response里的数据
            //     localStorage.setItem("Token", response.data)
            //     $("#ppp").text(response.data);
            //     $("#localStorage").text(localStorage.Token);
            //         // this.msg = "lsdjlkj"
            // }).catch(function (error) { // 请求失败处理
            //     $("#ppp").text(error)
            // });

            addIn.login("test", "123")
                
        },
        click2: function() {
            localStorage.clear();
            console.log("sldkjflkasjd", localStorage.length)
        }
    },
})

export default vm