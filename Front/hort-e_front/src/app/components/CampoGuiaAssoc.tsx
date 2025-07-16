import { useRouter } from 'expo-router';
import React, { useEffect, useState } from 'react';
import { Dimensions, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { useAuth } from '../hooks/useAuth';

const { height, width } = Dimensions.get('window');

type propsCampoGuiaAssoc = {
    titulo: string;
    conteudo: string;
}

export default function CampoGuiaAssoc({titulo, conteudo}: propsCampoGuiaAssoc) {

    const router = useRouter();
    const { token } = useAuth();
    const [guiaData, setGuiaData] = useState({
        id: '',
        title: '',
        ideais: '',
        image: '',
        content: '',
        plants: '',
    });
    const [textoExibido, setTextoExibido] = useState('');
    const [temGuia, setTemGuia] = useState(false);

    useEffect(() => {
        if (conteudo === 'Nenhum guia associado.') {
            setTextoExibido(conteudo);
        } else {
            const fetchGuiaAssociado = async () => {
                try {
                    const response = await fetch(`http://10.0.2.2:8080/api/guias/${conteudo}`, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                    });

                    const data = await response.json();
                    setGuiaData({
                        id: data.id,
                        title: data.guiaTitulo,
                        ideais: data.guiaTipo,
                        image: data.guiaImagemUrl,
                        content: data.guiaConteudo,
                        plants: data.plantasAssociadasIds,
                    });
                    setTextoExibido(data.guiaTitulo);
                    setTemGuia(true);
                } catch (error) {
                    //console.error("Erro ao buscar guia associado:", error);
                }
            };

            fetchGuiaAssociado();
        }
    }, [conteudo, token]);


    return (
        <View style={styles.container}>
            <View style={styles.tipoInput}>
                <Text style={styles.estiloTitulo}>{titulo}</Text>
            </View>
            <View style = {styles.botaoinput}>
                {temGuia ? (
                    <TouchableOpacity 
                        onPress={() => 
                            router.push({
                                pathname: '/screens/(isolated)/isolatedguide',
                                params: {
                                    id: guiaData.id,
                                    title: guiaData.title,
                                    ideais: guiaData.ideais,
                                    image: guiaData.image,
                                    content: guiaData.content,
                                    plants: guiaData.plants,
                                },
                            })
                        }
                    >
                        <Text style={styles.estiloConteudoLink}> {textoExibido} </Text>
                    </TouchableOpacity>
                ) : (
                    <Text style={styles.estiloConteudo}> {textoExibido} </Text>
                )}
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        width: width * 0.9,
        height: height * 0.085,
        marginTop: height * 0.03,
    },
    tipoInput: {
        height: '60%',
        marginLeft: width * 0.005,
    },
    botaoinput: {
        height: '60%',
    },
    estiloTitulo: {
        fontSize: 25,
        fontWeight: 'light',
    },
    estiloConteudoLink: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#4BD465'
    },estiloConteudo: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    estiloConteudoItalico: {
        fontSize: 18,
        fontWeight: 'bold',
        fontStyle: 'italic',
    }
})