import { Ionicons as Icon } from '@expo/vector-icons';
import React, { useEffect, useState } from 'react';
import {
    Dimensions,
    Image,
    Modal,
    ScrollView,
    StyleSheet,
    Text,
    TextInput,
    TouchableOpacity,
    View,
} from 'react-native';
import { useAuth } from '../hooks/useAuth';

const { height, width } = Dimensions.get('window');

type Comentario = {
    id: number;
    comentarioTexto: string;
    comentarioData: string;
};

type PostProps = {
    post: {
        id: number;
        postTexto: string;
        postImagemUrl: string;
        postData: string;
        autor: {
            username: string;
            usuarioImagemUrl: string;
            };
        comentarios: Comentario[];
    };
};

export default function Post({ post }: PostProps) {
    const [modalVisible, setModalVisible] = useState(false);
    const [comentarios, setComentarios] = useState<Comentario[]>(post.comentarios || []);
    const [novoComentario, setNovoComentario] = useState('');
    const {token} = useAuth();

    const carregarComentarios = async () => {
        const idDoPost = post.id
        try {
            const response = await fetch(`http://10.0.2.2:8080/api/posts/${idDoPost}/comentarios`, {
                                        headers: {
                                            'Authorization': `Bearer ${token}`,
                                            'Content-Type': 'application/json',
                                        },
            });
            const data = await response.json();
            setComentarios(data);
        } catch (error) {
            console.error('Erro ao carregar comentários:', error);
        }
    };

    const enviarComentario = async () => {
        if (!novoComentario.trim()) return;

        try {
            const idDoPost = post.id
            await fetch(`http://10.0.2.2:8080/api/posts/${idDoPost}/comentarios`, {
                    method: 'POST',
                    headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                    body: JSON.stringify({ comentarioTexto: novoComentario }),
            });

            setNovoComentario('');
            await carregarComentarios(); // atualiza lista
        } catch (error) {
            console.error('Erro ao enviar comentário:', error);
        }
    };

    useEffect(() => {
        if (modalVisible) {
            carregarComentarios();
        }
    }, [modalVisible]);

    return (
        <View style={styles.container}>
            <View style={styles.superior}>
                <Image source={{ uri: post.autor.usuarioImagemUrl }} style={styles.avatar} />
                <Text style={styles.username}>{post.autor.username}</Text>
            </View>

            <View style={styles.meio}>
                <Text style={styles.texto}>{post.postTexto}</Text>
            </View>

            <Image source={{ uri: post.postImagemUrl }} style={styles.imagem} />

            <View style={styles.inferior}>
                <TouchableOpacity onPress={() => setModalVisible(true)}>
                <Icon name="chatbubble-outline" size={42} color="black" />
                </TouchableOpacity>
                <Icon name="share-outline" size={42} color="black" />
            </View>

            <Modal
                visible={modalVisible}
                animationType="slide"
                transparent={true}
                onRequestClose={() => setModalVisible(false)}
            >
                <View style={styles.modalOverlay}>
                    <View style={styles.modalContainer}>
                        <Text style={styles.modalTitle}>Comentários</Text>
                        <ScrollView style={styles.commentList}>
                            {comentarios.length > 0 ? (
                                comentarios.map((comentario) => (
                                <Text key={comentario.id} style={styles.comentario}>
                                    {comentario.comentarioData} : {comentario.comentarioTexto}
                                </Text>
                                ))
                            ) : (
                                <Text style={styles.semComentarios}>Seja o primeiro a comentar!.</Text>
                            )}
                        </ScrollView>

                        <View style={styles.inputContainer}>
                        <TextInput
                            style={styles.textInput}
                            value={novoComentario}
                            onChangeText={setNovoComentario}
                            placeholder="Escreva um comentário..."
                        />
                        <TouchableOpacity onPress={enviarComentario} style={styles.enviarButton}>
                            <Text style={styles.enviarButtonText}>Enviar</Text>
                        </TouchableOpacity>
                        </View>

                        <TouchableOpacity onPress={() => setModalVisible(false)} style={styles.closeButton}>
                        <Text style={styles.closeButtonText}>Fechar</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </Modal>
        </View>
    );
}



const styles = StyleSheet.create({
    container:{
        width: width * 1,
        height: height * 0.5,
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: height * 0.01,
    },
    superior:{
        width: '100%',
        height: '20%',
        backgroundColor: '#FFFFFF',
        position: 'absolute',
        flexDirection: 'row',
        gap: width * 0.03,
        top: 0,
        left: width * 0.02,
    },
    meio: {
        width: '100%',
        height: '20%',
        backgroundColor: '#FFFFFF',
        position: 'absolute',
        top: height * 0.05,
    },
    inferior:{
        width: '100%',
        height: '15%',
        backgroundColor: '#FFFFF',
        position: 'absolute',
        flexDirection: 'row',
        justifyContent: 'space-between',
        paddingHorizontal: width * 0.04,
        paddingTop: height * 0.021,
        bottom: height * 0.025,
    },
    imagem: {
        width: '100%',
        height: '60%',
        resizeMode: 'cover',
    },
    avatar: {
        width: 40,
        height: 40,
        borderRadius: 20,
        marginTop: height * 0.003
    },
    username: {
        fontWeight: 'bold',
        fontSize: 16,
        marginTop: height * 0.01,
    },
    texto: {
        fontSize: 14,
        marginTop: height * 0.005,
        marginLeft: width * 0.03,
        marginRight: width * 0.03,
    },
    modalOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0,0,0,0.1)',
        justifyContent: 'flex-end',
    },
    modalContainer: {
        backgroundColor: '#fff',
        padding: width * 0.05,
        borderTopLeftRadius: 20,
        borderTopRightRadius: 20,
        maxHeight: height * 0.9,
    },
    modalTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 10,
    },
    commentList: {
        maxHeight: height * 0.9,
    },
    comentario: {
        fontSize: 14,
        color: '#000',
        marginBottom: 10,
    },
    semComentarios: {
        fontSize: 14,
        color: '#000',
        fontStyle: 'italic',
    },
    closeButton: {
        alignSelf: 'center',
        marginTop: 15,
        backgroundColor: '#2E8B57',
        paddingVertical: 8,
        paddingHorizontal: 20,
        borderRadius: 20,
    },
    closeButtonText: {
        color: '#fff',
        fontSize: 14,
        fontWeight: 'bold',
    },
    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginTop: 10,
    },
    textInput: {
        flex: 1,
        borderColor: '#ccc',
        borderWidth: 1,
        borderRadius: 15,
        paddingVertical: 6,
        paddingHorizontal: 12,
        fontSize: 14,
        backgroundColor: '#f5f5f5',
    },
    enviarButton: {
        marginLeft: 10,
        backgroundColor: '#2E8B57',
        paddingHorizontal: 15,
        paddingVertical: 8,
        borderRadius: 15,
    },
    enviarButtonText: {
        color: '#fff',
        fontWeight: 'bold',
    },


})