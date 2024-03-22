import { useState } from 'react';
import { Card, CardBody, Text, IconButton, Flex, HStack, Input } from '@chakra-ui/react';
import { EditIcon, DeleteIcon, CheckIcon, LockIcon } from '@chakra-ui/icons';
import { Task } from '../../model/Task';

interface TaskCardProps {
    onUpdate: (task: Task) => void;
    onDelete: (taskId: number) => void;
    task: Task;
}

const TaskCard: React.FC<TaskCardProps> = ({ task, onUpdate, onDelete }) => {
    const apiUrl = import.meta.env.VITE_API_URL;
    const [isEditing, setIsEditing] = useState(false);
    const [editedDescription, setEditedDescription] = useState(task.description);

    const handleToggleStatus = async () => {
        try {
            // Perform the API request to mark the task as completed
            const response = await fetch(apiUrl + `/api/tasks/${task.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ ...task, completed: !task.completed }),
            });

            if (response.status === 200) {
                // Task marked as completed successfully
                onUpdate({ ...task, completed: !task.completed });
            } else {
                const data = await response.json();
                if (data.errors) {
                    alert(data.errors[0].defaultMessage);
                } else {
                    alert('Failed to mark the task as completed');
                }
            }
        } catch (error) {
            // Handle any network or request error
            console.error('Error marking task as completed:', error);
        }
    };

    const handleEditTask = async () => {
        if (isEditing) {
            try {
                const updatedTask = { ...task, description: editedDescription };
                const response = await fetch(apiUrl + `/api/tasks/${task.id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(updatedTask),
                });

                if (response.status === 200) {
                    setIsEditing(false);
                    onUpdate(updatedTask);
                } else {
                    const data = await response.json();
                    if (data.errors) {
                        alert(data.errors[0].defaultMessage);
                    } else {
                        alert('Failed to save edited task');
                    }
                }
            } catch (error) {
                console.error('Error saving edited task:', error);
            }
        } else {
            setIsEditing(true);
        }
    };

    const handleDeleteTask = async () => {
        try {
            // Perform the API request to delete the task
            const response = await fetch(apiUrl + `/api/tasks/${task.id}`, {
                method: 'DELETE',
            });

            if (response.status === 200) {
                // Task deleted successfully
                onDelete(task.id);
            } else {
                const data = await response.json();
                if (data.errors) {
                    alert(data.errors[0].defaultMessage);
                } else {
                    alert('Failed to mark the task as completed');
                }
            }
        } catch (error) {
            // Handle any network or request error
            console.error('Error deleting task:', error);
        }
    };

    return (
        <Card mt={1}>
            <CardBody>
                <Flex alignItems="center" justifyContent="space-between">
                    <Text
                        style={{
                            textDecoration: task.completed ? 'line-through' : 'none',
                            opacity: task.completed ? 0.5 : 1,
                            fontWeight: 600,
                        }}
                    >
                        {isEditing ? (
                            <Input
                                type='text'
                                variant='outline'
                                placeholder='New task description'
                                value={editedDescription}
                                onChange={(e) => setEditedDescription(e.target.value)}
                            />
                        ) : (
                            task.description
                        )}
                    </Text>
                    <HStack alignItems="center">
                        <IconButton
                            size="sm"
                            colorScheme={task.completed ? "green" : "gray"}
                            aria-label="Mark as completed"
                            icon={<CheckIcon />}
                            onClick={handleToggleStatus}
                        />
                        <IconButton
                            size="sm"
                            colorScheme={isEditing ? 'teal' : 'yellow'}
                            aria-label={isEditing ? 'Save the task' : 'Edit the task'}
                            icon={isEditing ? <LockIcon /> : <EditIcon />}
                            onClick={handleEditTask}
                        />
                        <IconButton
                            size="sm"
                            colorScheme="red"
                            aria-label="Delete the task"
                            icon={<DeleteIcon />}
                            onClick={handleDeleteTask}
                        />
                    </HStack>
                </Flex>
            </CardBody>
        </Card>
    );
};

export default TaskCard;
