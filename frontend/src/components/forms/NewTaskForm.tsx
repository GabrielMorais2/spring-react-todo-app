import React, { useState } from 'react';
import { Box, Input, Button, FormControl } from '@chakra-ui/react';
import { AddIcon } from '@chakra-ui/icons';
import { Task } from '../../model/Task';

interface NewTaskFormProps {
    onSave: (task: Task) => void;
}

const NewTaskForm: React.FC<NewTaskFormProps> = ({ onSave }) => {
    const apiUrl = import.meta.env.VITE_API_URL;
    const [taskDescription, setTaskDescription] = useState('');

    const handleSaveClick = async () => {
        try {
            // Client-side validation
            if (taskDescription.length < 2) {
                alert('Task description must be at least 2 characters long.');
                return;
            }

            if (taskDescription.length > 260) {
                alert('Task description cannot exceed 260 characters.');
                return;
            }

            // Create a new task object to send to the API
            const newTask = {
                description: taskDescription,
                completed: false, // Assuming a new task is not completed by default
            };

            // Make a POST request to your API
            const response = await fetch(apiUrl + '/api/tasks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newTask),
            });

            if (response.status === 201) {
                // Task created successfully
                const data = await response.json();
                onSave(data as Task); // Notify the parent component
                setTaskDescription(''); // Clear the input field
            } else {
                const data = await response.json();
                if (data.errors) {
                    alert(data.errors[0].defaultMessage);
                } else {
                    alert('Failed to create the task');
                }
            }
        } catch (error) {
            console.error('Error creating the task:', error);
        }
    };

    return (
        <Box display="flow" alignItems="center">
            <FormControl isRequired>
                <Input
                    placeholder="Write short but descriptive task description here..."
                    value={taskDescription}
                    onChange={(e) => setTaskDescription(e.target.value)}
                    minLength={2}
                    maxLength={260}
                />
            </FormControl>
            <Button
                leftIcon={<AddIcon />}
                colorScheme='teal'
                size='md'
                onClick={handleSaveClick}
                mt={2}
                width='100%'
                type='submit'
            >
                Add
            </Button>
        </Box>
    );
};

export default NewTaskForm;
