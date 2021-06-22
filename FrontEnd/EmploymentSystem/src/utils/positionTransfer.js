const num2position = {
    '0': '技术',
    '1': '产品',
    '2': '设计',
    '3': '运营',
    '4': '市场',
    '5': '人事',
    '6': '财务',
    '7': '行政',
    '8': '高级管理'
}

export function positionTransfer(arg) {
    return {
        key: num2position[arg],
    }
}