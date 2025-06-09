import * as SecureStore from 'expo-secure-store';
import React, { useEffect, useState } from "react";
import { Dimensions, ScrollView, StyleSheet, View } from "react-native";
import BannerPerfil from "../../components/BannerPerfil";
import FotoPerfil from "../../components/FotoPerfil";
import InputPerfil from "../../components/InputPerfil";
import { useAuth } from "../../hooks/useAuth";

const { height, width } = Dimensions.get("window");

export default function Profile() {

    const { token } = useAuth();
    const [userData, setUserData] = useState({
        imagem: '',
        nome: '',
        cidade: '',
        email: '',
        tipoHorta: ''
    });

    useEffect(() => {
        if (!token) return;
        async function fetchUserData() {
            try {
                const userId = await SecureStore.getItemAsync('userId');
                if (!userId) return;

                const response = await fetch(`http://10.0.2.2:8080/api/usuarios/${userId}/profile/id`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });
                const data = await response.json()

                setUserData({
                    imagem: data.usuarioImagemUrl || '',
                    nome: data.username || '',
                    cidade: data.localizacao || '',
                    email: data.usuarioEmail || '',
                    tipoHorta: data.hortaTipo || ''
                });

            } catch (error) {
                console.error('Erro ao buscar dados do usuário:', error);
            }
        }

        fetchUserData();
    }, [token]);


    return (
        <View style={styles.container}>
            <View style={styles.bannerVerde}>
                <BannerPerfil />
            </View>
            <View style = {styles.fotoEdit}>
                {userData.imagem !== '' && (
                    <FotoPerfil value={userData.imagem}/>
                )}
            </View>
            <ScrollView style={styles.scrollview}>
                <InputPerfil
                    titulo="Nome de Usuário:"
                    label="Digite seu nome..."
                    value={userData.nome}
                    onChangeText={(text) => setUserData((prev) => ({ ...prev, nome: text }))}
                />
                <InputPerfil
                    titulo="Cidade:"
                    label="Digite sua cidade..."
                    value={userData.cidade}
                    onChangeText={(text) => setUserData((prev) => ({ ...prev, cidade: text }))}
                />
                <InputPerfil
                    titulo="Email:"
                    label="Digite seu email..."
                    value={userData.email}
                    onChangeText={(text) => setUserData((prev) => ({ ...prev, email: text }))}
                />
                <InputPerfil
                    titulo="Tipo de Horta:"
                    label="Digite o tipo de horta..."
                    value={userData.tipoHorta}
                    onChangeText={(text) => setUserData((prev) => ({ ...prev, tipoHorta: text }))}
                />
                <View style={styles.blocoInvisivel}/>
            </ScrollView>
        </View>

    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#F8F8F8",
        justifyContent: "center",
        alignItems: "center",
    },
    scrollview: {
        marginTop: height * 0.25
    },
    blocoInvisivel: {
        height: height * 0.12,
        width: '100%'
    },
    bannerVerde: {
        position: 'absolute',
        top: 0,
        width: '100%',
    },
    fotoEdit: {
        position: 'absolute',
        top: height * 0.05,
    }
})
