import { Alert } from '@mui/material'
import React from 'react'

type AlertProps = {
    massage: string
}
function ErrorAlert(props: AlertProps) {
    return (
        <Alert  sx={{position:"absolute", width:"100vw"}} severity="error">
            {props.massage}
        </Alert>
    )
}

export default ErrorAlert
