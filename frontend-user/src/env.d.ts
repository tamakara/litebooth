/// <reference types="vite/client" />

// 声明 .vue 文件模块类型
declare module '*.vue' {
    import { DefineComponent } from 'vue'
    const component: DefineComponent<{}, {}, any>
    export default component
}
