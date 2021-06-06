import React, {useState} from 'react';
import {Button, ButtonToolbar, ControlLabel, Form, FormControl, FormGroup, Loader} from 'rsuite';
import styled from 'styled-components'

export const AuthFormContainer = styled.div`
    height:80vh;
    display: flex;
    align-items: center;
    justify-content: center;
`;
const AuthFormWrapper = styled.div`
   border: 1px solid #e5e5ea;
   padding:20px;
   border-radius:4px;
`;
const ButtonToolbarStyle = {
    color: 'blue',
    textAlign: "right",
};
const FormControlStyle = {
    width: 300,
}

function AuthFormComponent(props) {
    const {authUser,userState} = props;
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");

    return (
        <AuthFormContainer>
            <AuthFormWrapper>
                <Form fluid>
                    <FormGroup>
                        <ControlLabel>Имя пользователя</ControlLabel>
                        <FormControl style={FormControlStyle} placeholder="login" name="login"
                                     onChange={(value) => {
                                         setLogin(value);
                                     }} required/>
                        <ControlLabel>Пароль</ControlLabel>
                        <FormControl style={FormControlStyle} placeholder="*******" type="password" name="password"
                                     onChange={(value) => {
                                         setPassword(value);
                                     }}
                        />
                    </FormGroup>
                    <FormGroup style={ButtonToolbarStyle}>
                        <ButtonToolbar>
                            {
                                userState.logining &&
                                <Loader content="Авторизуемся..." />
                            }
                            {
                                userState.failedLogin &&
                                <span style={{color:'red',paddingTop:24,paddingRight:10}}>Ошибка входа</span>
                            }
                            <Button onClick={
                                authUser.bind(this, login, password)}
                                    appearance="primary">Войти</Button>
                        </ButtonToolbar>
                    </FormGroup>
                </Form>
            </AuthFormWrapper>
        </AuthFormContainer>
    );
}

export default AuthFormComponent;