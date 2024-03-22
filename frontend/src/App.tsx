import { useState, useEffect } from 'react';
import { Container, StackDivider, VStack, Box, Text, HStack, Button } from '@chakra-ui/react'
import { DeleteIcon } from '@chakra-ui/icons'
import NewTaskForm from './components/forms/NewTaskForm'
import TaskList from './components/ui/TaskList'
import { Task } from './model/Task';

const App = () => {
  const apiUrl = import.meta.env.VITE_API_URL;
  const [tasks, setTasks] = useState<Task[]>([]);
  const [filter, setFilter] = useState('all');

  // Function to fetch tasks from the API
  const fetchTasks = async () => {
    try {
      const response = await fetch(apiUrl + '/api/tasks');
      if (response.status === 200) {
        const data = await response.json();
        setTasks(data.data); // Update the tasks with the API response
      } else {
        console.error('Failed to fetch tasks');
      }
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };

  const batchDelete = async (completed: string) => {
    try {
      const response = await fetch(apiUrl + `/api/tasks/?completed=` + completed, {
        method: 'DELETE',
      });
      if (response.status === 200) {
        fetchTasks();
      } else {
        console.error('Failed to batch delete tasks');
      }
    } catch (error) {
      console.error('Error deleting tasks:', error);
    }
  };

  // Load tasks from the API when the component mounts (on page load)
  useEffect(() => {
    fetchTasks();
  }); // The empty dependency array ensures this effect runs once on component mount

  const filteredTasks = filter === 'all'
    ? tasks
    : filter === 'completed'
      ? tasks.filter(task => task.completed)
      : tasks.filter(task => !task.completed);

  const handleSave: (task: Task) => void = (task) => {
    const newTask: Task = {
      id: task.id,
      description: task.description,
      completed: task.completed,
    };
    setTasks([...tasks, newTask]);
  };

  const handleUpdate: (task: Task) => void = (task) => {
    const updatedTasks = tasks.map((t) => {
      if (t.id === task.id) {
        return task;
      }
      return t;
    });
    setTasks(updatedTasks);
  };

  const handleDelete: (taskId: number) => void = (taskId) => {
    const updatedTasks = tasks.filter((task) => task.id !== taskId);
    setTasks(updatedTasks);
  };

  return (
    <>
      <Container centerContent={true}>
        <VStack
          divider={<StackDivider borderColor='gray.200' />}
          spacing={8}
          align='stretch'
          width='100%'
        >
          <Box textAlign='center'>
            <Text fontSize='4xl'>TODO List</Text>
          </Box>
          <Box>
            <NewTaskForm onSave={handleSave}></NewTaskForm>
          </Box>
          <Box>
            <HStack spacing={0} justifyContent='space-between'>
              <Button
                onClick={() => setFilter('all')}
                colorScheme={filter === 'all' ? 'blue' : 'gray'}
                flex='1'
                mr={2}
              >
                All Tasks ({tasks.length})
              </Button>
              <Button
                onClick={() => setFilter('completed')}
                colorScheme={filter === 'completed' ? 'green' : 'gray'}
                flex='1'
                mr={2}
              >
                Completed ({tasks.filter(task => task.completed).length})
              </Button>
              <Button
                onClick={() => setFilter('pending')}
                colorScheme={filter === 'pending' ? 'yellow' : 'gray'}
                flex='1'
              >
                Pending ({tasks.filter(task => !task.completed).length})
              </Button>
            </HStack>
          </Box>
          <Box>
            <TaskList tasks={filteredTasks} onUpdate={handleUpdate} onDelete={handleDelete} />
          </Box>
          <Box>
            <HStack spacing={0} justifyContent='space-between'>
              <Button
                leftIcon={<DeleteIcon />}
                onClick={() => batchDelete('true')}
                colorScheme='red'
                flex='1'
                mr={2}
              >
                Delete completed
              </Button>
              <Button
                leftIcon={<DeleteIcon />}
                onClick={() => batchDelete('all')}
                colorScheme='red'
                flex='1'
                mr={2}
              >
                Delete all
              </Button>
            </HStack>
          </Box>
        </VStack>
      </Container>
    </>
  );
};

export default App;
